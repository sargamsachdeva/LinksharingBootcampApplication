package com.ttn.linksharingbootcamp

import constants.Constants
import vo.PostVO

class LinkSharingTagLib {
    static defaultEncodeAs = "raw"
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def showSubscribedTopics = {
        User user = session.user


        if (user) {
            out << "${g.select(class: 'btn dropdown-toggle col-sm-8 form-control', name: 'topic', from: user?.getSubscribedTopics() , optionKey: 'id')}"
        }
    }


    def markRead = { attrs, body ->
        User user = session.user
        if (user) {
            Resource resource = Resource.get(attrs.id)
            if (resource) {
                Integer count = ReadingItem.countByResourceAndIsReadAndUser(resource, true, user)
                if (count) {
                    out << "<a href='${createLink(controller: 'readingItem', action: 'toggleIsRead', id: resource.id)}'>Mark as UnRead</a> "
                } else {
                    out << "<a href='${createLink(controller: 'readingItem', action: 'toggleIsRead', id: resource.id)}'>Mark as Read</a> "
                }
            }
        }
    }

        def getTopPostss = {

        List<PostVO> postVOList = Resource.getTopPosts()
        out << render(template: '/login/topPost', model: [postVOList: postVOList])
    }

    def recentShares = {

        List<PostVO> recentPostList = Resource.recentShares()
        out << render(template: "/login/recentShares", model: [recentPostList: recentPostList])
    }

    def resourceType = { attrs, body ->
        Long resourceID = attrs.resourceID
        String resourceType = Resource.checkResourceType(Resource?.get(resourceID))
        String resourceLink = attrs.url
        if (resourceType == Constants.LINK_RESOURCE_TYPE) {
            out << "<a href='${resourceLink}' class='text-right' style=display:block target='_blank'>View Full Site</a>"
        } else if (resourceType == Constants.DOCUMENT_RESOURCE_TYPE) {
            out << "<a href='${createLink(controller: 'documentResource', action: 'download', params: [id: resourceID])}' "+
            "class='text-right' style=display:block>Download</a>"
        }
    }

    def canDeleteResource = { attrs, body ->
        Long resourceID = attrs.resourceID
        User user = session.user
        if (user) {
            if (User.ifUserCanEditDeleteResource(Resource?.get(resourceID), user)) {
                out << "<a href='${createLink(controller: 'Resource', action: 'delete', params: [id: resourceID])}' " +
                        "class='text-right' style=display: block'>Delete</a>"
            }
        }
    }

    def showSubscribe = { attrs, body ->

        if (session.user) {
            Long topicID = attrs.topicID
            if (topicID) {
                if (Topic.isTopicSubscribedByUser(Topic?.get(topicID), session.user)) {
                    out << "<a href='${createLink(controller: 'subscription', action: 'delete', params: [userId: session.user.id, topicId: topicID])}'>UnSubscribe</a>"
                } else {
                    out << "<a href='${createLink(controller: 'subscription', action: 'save', params: [userId: session.user.id, topicId: topicID])}'>Subscribe</a>"
                }

            }
        }
    }

    def subscriptionCount = { attrs, body ->
        Long userId = attrs.userId
        Long topicId = attrs.topicId
        if (userId) {
            out << Subscription.countByUser(User.load(userId))
        }
        if (topicId) {
            out << Subscription.countByTopic(Topic.load(topicId))
        }
    }

    def resourceCount = { attrs, body ->
        Long topicId = attrs.topicId
        if (topicId) {
            out << Resource.countByTopic(Topic.load(topicId))
        }
    }

    def topicCount = { attrs, body ->
        Long userId = attrs.userId
        if (userId) {
            out << Topic.countByCreatedBy(User.load(userId))
        }
    }

    def userImage = { attrs, body ->

        Long userId = attrs.userId
        String height = attrs.height
        String width = attrs.width
        String tagClass = attrs.class
        out << "<img src='${createLink(controller: 'user', action: 'image', params: [id: userId])}' " +
                "class='${tagClass}' height='${height}' width='${width}'>"
    }

    def canUpdateTopic = { attrs, body ->
        out << body()
    }

    def showSeriousness = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user) {
            if (topic) {
                Subscription subscription = user.ifSubscriptionExist(topic)
                if (subscription) {
                    out << "${g.select(onClick:'submit()',name:'seriousness', id: 'seriousness', from: enums.Seriousness.values(), class: 'btn btn-xs btn-default dropdown-toggle seriousness',tid:topicId, value:subscription.seriousness)}" }
            }
        }
    }

    def showVisibility = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user) {
            if (topic) {
                if (User.isUserAdmin(user) || User.ifTopicIsCreatedBy(topic, user)) {
                    out << "${g.select(name: 'visibility', from: enums.Visibility.values(), class: 'btn btn-xs btn-default dropdown-toggle visibility', topicId: topicId, topicName: topic.topicName, createdBy: user, value: topic.visibility)}"
                }
            }
        }
    }

    def sendTopicInvite = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (User.isUserAdmin(user) || User.ifTopicIsCreatedBy(topic, user) || Subscription.findByUserAndTopic(user, topic)) {
                out << "<a id='inviteModalBtn' role='button' data-toggle='modal' data-topicid='$topicId' data-target='#sendInvite'>" +
                        "<span class='glyphicon glyphicon-envelope'></span></a>"
            }
        }
    }

    def editTopicDetails = { attrs, body ->
        User user = session.user
        Long topicId= attrs.topicId
        Topic topic = Topic.get(topicId)
        log.info(".....>>"+topicId)
        log.info(".......>>"+user)
        if (user && topic) {
            if (User.isUserAdmin(user) || User.ifTopicIsCreatedBy(topic, user)) {
                out << "<a id='inviteModalBtn' role='button' data-toggle='modal' data-topicid='$topicId' data-target='#topicEditModal'>" +
                        "<span class='glyphicon glyphicon-edit'></span></a>"

            }
        }
    }

    def canDeleteTopic = { attrs, body ->
        User user = session.user
        Long topicId = attrs.topicId
        Topic topic = Topic.get(topicId)
        if (user && topic) {
            if (User.isUserAdmin(user) || User.ifTopicIsCreatedBy(topic, user)) {
                out << "<a href='${createLink(controller: 'topic', action: 'delete', params: [id: topicId])}'><span " +
                        "class='glyphicon glyphicon-trash' style='font-size:20px'></span></a>"
            }
        }
    }

    def editResourceDetails = { attrs, body ->
        User user = session.user
        Long resourceId = attrs.resourceId
        Resource resource = Resource.get(resourceId)
        if (user && resource) {
            if (User.isUserAdmin(user) || User.isResourceCreatedBy(resource, user)) {
                out << "<a class=\"btn\" id=\"resourceEdit\" role=\"button\" data-toggle=\"modal\"\n" +
                        "                           data-target=\"#resourceEditModal\"\n" +
                        "                           params=\"[id:${resourceId},description:${resource.description}]\">\n" +
                        "                            <ins>Edit</ins>\n" +
                        "                        </a>"

            }
        }
    }

    def imgFromByteArray = { attrs ->

        //Get image content and type from attrs
        Long userId = attrs.userId
        byte[] imageContent = attrs.imageBytes
        String imageType = attrs.imageType

        //Remove attributes that should not be printed in the img tag
        ['imageBytes', 'imageType'].each{
            if(attrs.containsKey(it)){ attrs.remove(it) }
        }

        //Print the image tag with image content
        out << """<img src='data:${imageType};base64,${imageContent?.encodeBase64()}'"""

        //Include any other tags given
        attrs.each { attrLabel, attrValue ->
            out << " ${attrLabel}=\"${attrValue}\""
        }

        //Close the image tag
        out<< "/>"
    }

}
