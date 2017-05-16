package com.ttn.linksharingbootcamp

import co.ResourceSearchCO
import constants.Constants
import enums.Visibility
import vo.PostVO
import vo.RatingInfoVO

abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated


    static hasMany = [ratings     : ResourceRating,
                      readingItems: ReadingItem]

    static transients = ['ratingInfo']

    static mapping = {
        description(type: 'text')
    }

    static belongsTo = [topic: Topic]

    static constraints = {
        description(blank: false)
    }


    String toString() {
        return "${description}"
    }

    static namedQueries = {

        search { ResourceSearchCO resourceSearchCO ->
            if (resourceSearchCO.q) {
                if (resourceSearchCO.topicId) {
                    eq('topic.id', resourceSearchCO.topicId)
                }

                if (resourceSearchCO.visibility && resourceSearchCO.visibility == Visibility.PUBLIC) {
                    'topic' {
                        eq('visibility', Visibility.PUBLIC)
                    }
                }

                ilike('description', "%${resourceSearchCO.q}%")
            }

        }
    }

    RatingInfoVO getRatingInfo() {

        List result = ResourceRating.createCriteria().get {
            projections {
                count('id', 'totalVotes')
                avg('score')
                sum('score')
            }
            eq('resource', this)
            order('totalVotes', 'desc')
        }
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }

    static List<PostVO> recentShares()
    {
        List<PostVO>  recentPostList =[]

        Resource.createCriteria().list(max:4){
            projections{
                property('description')
                'createdBy'{
                    property('firstName')
                    property('userName')
                    property('photo')

                }
                'topic' {

                    property('topicName')
                    property('lastUpdated')
                    eq('visibility', enums.Visibility.PUBLIC)
                }
            }

        }?.each{
            recentPostList.add(new PostVO( description: it[0],userFirstName: it[1], userName: it[2],
                    userPhoto: it[3],topicName: it[4],postDate: it[5]))
        }

        return recentPostList
    }


    /*static List<PostVO> getRecentShares() {

        List<PostVO> recentShares=[]

        Resource.createCriteria().list(max:2){

            projections {
               *//* property('description')*//*
                'createdBy' {
                    property('userName')
                    property('firstName')
                }

                'topic' {
                    property('topicName')
                }

            }
        }?.each {

            recentShares.add(new PostVO(userFirstName: it[0],userName: it[1],topicName: it[2]))
        }
        return recentShares
    }
    */

    static List<PostVO> getTopPosts() {
        List<PostVO> topPosts = []
        ResourceRating.createCriteria().list(max: 2) {
            projections {
                property('resource.id')

                'resource'{
                    property('description')
                    property('url')
                    property('filePath')
                    'topic' {
                        property('id')
                        property('topicName')
                        eq('visibility', Visibility.PUBLIC)
                    }
                    'createdBy' {
                        property('id')
                        property('userName')
                        property('firstName')
                        property('lastName')
                        property('photo')
                    }
                    property('lastUpdated')
                }
            }
            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }?.each {
            topPosts.add(new PostVO(resourceID: it[0], description: it[1], url: it[2], filePath: it[3], topicID:
                    it[4], topicName: it[5], userID: it[6], userName: it[7], userFirstName: it[8], userLastName: it[9],
                    userPhoto: it[10], isRead: "", resourceRating: 0, postDate: it[11]))
        }

        return topPosts
    }

    static String checkResourceType(Resource resource) {
        if (resource) {
            if (resource.class.equals(LinkResource)) {
                return Constants.LINK_RESOURCE_TYPE
            } else if (resource.class.equals(DocumentResource)) {
                return Constants.DOCUMENT_RESOURCE_TYPE
            }
        }
    }

    static Boolean ifResourceCanBeViewedBy(Resource resource, User user) {
        return user && resource && (Topic.ifTopicCanbeViewedBy(resource.topic, user))
    }

    static def topPosts() {
        return ResourceRating.createCriteria().list(max: 2) {
            projections {
                property('resource.id')
                'resource' {
                    property('description')
                    property('url')
                    property('filePath')
                    'topic' {
                        property('id')
                        property('topicName')
                        eq('visibility', Visibility.PUBLIC)
                    }
                    'createdBy' {
                        property('id')
                        property('userName')
                        property('firstName')
                        property('lastName')
                        property('photo')
                    }
                    property('lastUpdated')
                }
            }
            groupProperty('resource.id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }

    }


    static def recentPosts() {
        return Resource.createCriteria().list(max: 5) {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('topicName')
                    eq('visibility', enums.Visibility.PUBLIC)
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            order('lastUpdated', 'desc')
        }
    }

    def post() {
        return Resource.createCriteria().get {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('topicName')
                }
                'readingItems' {
                    property('isRead')

                    eq('user',createdBy)
                    eq('resource',this)
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            eq('id', this.id)
        }
    }

    static def topicPosts(Long id) {
        return Resource.createCriteria().list(max: 5) {
            projections {
                property('id')
                property('description')
                property('url')
                property('filePath')
                'topic' {
                    property('id')
                    property('topicName')
                }
                'createdBy' {
                    property('id')
                    property('userName')
                    property('firstName')
                    property('lastName')
                    property('photo')
                }
                property('lastUpdated')
            }
            eq('topic.id', id)
            order('lastUpdated', 'desc')
        }
    }


}
