package com.ttn.linksharingbootcamp

import co.TopicSearchCO
import constants.Constants
import enums.Seriousness
import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    def search(TopicSearchCO topicSearchCO) {

        if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            return user.getSubscribedTopics()
        }
    }

    Subscription saveSubscription(Subscription subscription, User user) {
        if (subscription.validate()) {

            user.confirmPassword=user.password

            subscription.topic.resources.each
                    {
                        if (!user.readingItems?.contains(it))
                        {
                            ReadingItem readingItem = new ReadingItem(user: user, resource:it, isRead: false)
                            if (readingItem.save(flush:true))
                            {
                                log.info "${readingItem} saved in ${user}'s list"

                                it.addToReadingItems(readingItem)
                                user.addToReadingItems(readingItem)
                            }
                            else {
                                log.error "${readingItem} is not saved in ${user}'s list--- ${readingItem.errors.allErrors}"
                            }
                        }
                    }
            return subscription.save(flush: true)
        }
        return null
    }

    def deleteSubscription(Subscription subscription) {
        subscription.delete(flush: true)
    }

    Subscription saveSubscription(Topic topic, User user) {
        if (user && topic) {
            Subscription subscription = new Subscription(topic: topic, user: user, seriousness:Constants.SERIOUSNESS)
            return saveSubscription(subscription,user)
        }
        return null
    }

    def deleteSubscription(Topic topic, User user) {
        if (user && topic) {
            if (Topic.isTopicSubscribedByUser(topic, user) && !User.ifTopicIsCreatedBy(topic, user)) {
                deleteSubscription(Subscription.findByUserAndTopic(user, topic))
                return true
            } else {
                return false
            }
        }
        return null
    }

    def updateSubscription(Subscription subscription, String serious) {
        if (subscription && serious) {
            subscription.seriousness = Seriousness.checkSeriousness(serious)
            log.info("subscription.seriousnes is-->${subscription.seriousness}")
            return saveSubscription(subscription)
        }
        return null
    }


}
