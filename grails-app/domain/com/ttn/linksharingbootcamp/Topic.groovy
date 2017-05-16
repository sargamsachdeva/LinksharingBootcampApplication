package com.ttn.linksharingbootcamp

import constants.Constants
import enums.Visibility
import vo.PostVO
import vo.TopicVO;

class Topic {

    String topicName;
    User createdBy;
    Date dateCreated;
    Date lastUpdated;
    Visibility visibility

   static belongsTo = [User]

    static hasMany = [subscriptions: Subscription,
                      resources    : Resource]

    static mapping = {

        sort "topicName" : 'asc'
    }

    static constraints = {

        topicName(unique: ['createdBy'], blank: false)
    }

    String toString() {
        return "${topicName}, ${createdBy}"
    }

    Topic topicDetails() {
        return this
    }

    def subscribedUsers() {
        return Subscription.createCriteria().list {
            projections {
                property('user')
            }
            eq('topic', this)
        }
    }

    static List<TopicVO> getTrendingTopics() {

        List<TopicVO> trendingTopics = []

        Resource.createCriteria().list {
            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.topicName')
                property('t.visibility')
                count('t.id', 'topicCount')
                property('t.createdBy')
            }
            order('topicCount', 'desc')
            order('t.topicName', 'asc')
            maxResults(5)
        }?.each {
            trendingTopics.add(new TopicVO(id: it[0], topicName: it[1], visibility: it[2], count: it[3], createdBy: it[4]))
        }

        return trendingTopics
    }

    static List<PostVO> getTopPosts() {

    }

    static Boolean isTopicPublic(Topic topic) {
        return topic && topic.visibility == Visibility.PUBLIC
    }

    static Boolean ifTopicCanbeViewedBy(Topic topic, User user) {
        return topic && (isTopicPublic(topic) || User.isUserAdmin(user) || isTopicSubscribedByUser(topic, user))
    }

    static Boolean isTopicSubscribedByUser(Topic topic, User user) {
        return user && topic && Subscription.findByUserAndTopic(user, topic)
    }


    static Boolean ifResourceCanBeViewedBy(Resource resource, User user) {
        return user && resource && (ifTopicCanbeViewedBy(resource.topic, user))
    }


    def afterInsert() {

        Topic.withNewSession {

            Subscription subscription = new Subscription(
                    user: this.createdBy,
                    topic: this,
                    seriousness: Constants.SERIOUSNESS)

            if (subscription.validate() && subscription.save(flush: true)) {
                log.info "${subscription}-> ${this.createdBy} subscribed for ${this}"

            } else {
                log.error "Subscription does not occured--- ${subscription.errors.allErrors}"
            }
        }
    }



    static List<Topic> trendingTopics() {
        return Resource.createCriteria().list {
            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.topicName')
                property('t.visibility')
                count('t.id', 'topicCount')
                property('t.createdBy')
            }
            order('topicCount', 'desc')
            order('t.topicName', 'asc')
            maxResults(2)
        }
    }

    def getSubscribedUsers() {
        return Subscription.createCriteria().list {
            projections {
                property('user')
            }
            eq('topic', this)
        }
    }

}
