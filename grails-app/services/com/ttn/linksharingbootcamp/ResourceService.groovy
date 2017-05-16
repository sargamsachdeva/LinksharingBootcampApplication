package com.ttn.linksharingbootcamp

import co.DocumentResourceCO
import co.LinkResourceCO
import co.ResourceSearchCO
import constants.Constants
import grails.transaction.Transactional
import vo.conversion.DomainToVO

@Transactional
class ResourceService {


    def userService
    def topicService
    def grailsApplication

    Resource saveResource(Resource resource) {
        if (resource.validate()) {
            return resource.save(flush: true)
        }
        return null
    }

    ResourceRating saveResourceRating(ResourceRating resourceRating) {
        if (resourceRating.validate()) {
            return resourceRating.save(flush: true)
        }
        return null
    }

    ResourceRating saveRating(Resource resource, User user, Integer score) {
        if (user && resource) {
            ResourceRating resourceRating = ResourceRating.findOrCreateByResourceAndUser(resource, user)
            if (resourceRating) {
                resourceRating.score = score
                return saveResourceRating(resourceRating)
            }
        }
        return null
    }

    File downloadDocumentResource(DocumentResource documentResource, User user) {

        if (documentResource && Topic.ifTopicCanbeViewedBy(documentResource.topic, user)) {
            def file = new File(documentResource.filePath)
            if (file.exists()) {
                return file
            }
        }
        return null
    }

    def search(ResourceSearchCO resourceSearchCO) {

        Resource.search(resourceSearchCO).list

    }

    def show(Resource resource, User user) {

        Boolean canView = false
        if (resource) {
            if (user) {
                if (Topic.ifResourceCanBeViewedBy(resource, user)) {
                    canView = true
                }

            } else {
                if (Topic.isTopicPublic(resource.topic)) {
                    canView = true
                }
            }
            if (canView) {
                return DomainToVO.post(resource)
            }
        }
        return null
    }

    Resource editResourceDescription(Resource resource, String description) {

        if (resource && description) {
            resource.description = description
            return saveResource(resource)
        }
        return null
    }


    def deleteResource(Resource resource) {
        resource.delete(flush: true)
    }

    def deleteResource(Resource resource, User user) {
        if (Resource) {
            if (User.ifUserCanEditDeleteResource(resource, user)) {
                def resourceType = Resource.checkResourceType(resource)
                if (resourceType == Constants.DOCUMENT_RESOURCE_TYPE) {
                    DocumentResource documentResource = (DocumentResource) resource
                    String filePath = documentResource.filePath
                    if (filePath && (new File(filePath).delete())) {
                        deleteResource(resource)
                        return true
                    } else {
                        deleteResource(resource)
                        return true
                    }
                } else if (resourceType == Constants.LINK_RESOURCE_TYPE) {
                    deleteResource(resource)
                    return true
                }
            }
        }
        return null
    }

    def saveLinkResource(LinkResourceCO linkResourceCO, User user1)

    {
        LinkResource linkResource = new LinkResource(url: linkResourceCO.url,description:linkResourceCO.description, topic:Topic.get(linkResourceCO.topic), createdBy: linkResourceCO.createdBy)

        List<User> subscribedUsers = linkResource.topic.getSubscribedUsers()


        subscribedUsers.each
                {
                    if (it.id != user1.id)
                    {
                        linkResource.addToReadingItems(new ReadingItem(
                                user: it,
                                resource: linkResource,
                                isRead: false)
                        )
                    }
                    else
                    {

                        linkResource.addToReadingItems(new ReadingItem(user: it, resource: linkResource, isRead: true))
                    }
                }


        if(linkResource.save(flush:true))
        {
            return linkResource
        }
        else
        {
            return null
        }

    }

    def saveResource(DocumentResource documentResource, def sourceFile, def destinationFile, User user1) {

        if (documentResource.validate()) {

            List<User> subscribedUsers = documentResource.topic.getSubscribedUsers()


            subscribedUsers.each
                    {
                        if (it.id != user1.id)
                        {
                            documentResource.addToReadingItems(new ReadingItem(
                                    user: it,
                                    resource: documentResource,
                                    isRead: false)
                            )
                        }
                        else
                        {

                            documentResource.addToReadingItems(new ReadingItem(user: it, resource: documentResource, isRead: true))
                        }
                    }


            sourceFile.transferTo(destinationFile)
            return documentResource.save(flush: true)
        }
        return null
    }


    DocumentResource uploadDocumentResource(DocumentResourceCO documentResourceCO, def sourceFile, User user) {

        String filePath = "/home/sargam/LinkSharingBootcamp/grails-app/assets/images/grails_user_file/${UUID.randomUUID()}.pdf"

        if (sourceFile.empty) {
            return null
        }

        else {


            DocumentResource documentResource = new DocumentResource(description: documentResourceCO.description,topic: Topic.get(documentResourceCO.topic), createdBy: documentResourceCO.createdBy)

            documentResource.filePath = filePath
            def destinationFile = new File(filePath)
            if (destinationFile) {
                return saveResource(documentResource, sourceFile, destinationFile, user)
            }
        }
    }

    def addToReadingItems(Resource resource, User sessionUser) {
        List<User> subscribedUsers = resource.topic.subscribedUsers()
        subscribedUsers.each {
            if (it.id != sessionUser.id) {
                resource.addToReadingItems(new ReadingItem(
                        user: it,
                        resource: resource,
                        isRead: false)
                )
            }
        }
    }



}