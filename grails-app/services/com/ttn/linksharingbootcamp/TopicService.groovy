package com.ttn.linksharingbootcamp

import co.TopicCO
import co.TopicSearchCO
import constants.Constants
import enums.Seriousness
import enums.Visibility
import grails.transaction.Transactional
import vo.TopicVO

@Transactional
class TopicService {

    def subscriptionService

    Topic saveTopic(Topic topic) {
        if (topic.validate()) {
            return topic.save(flush: true)
        }
        return null
    }

    def search(TopicSearchCO topicSearchCO) {

        List<TopicVO> createdTopics = []
        if (topicSearchCO.id) {
            List<Topic> topicList = Topic.createCriteria().list {
                eq('createdBy.id', topicSearchCO.id)
                if (topicSearchCO.visibility) {
                    eq('visibility', topicSearchCO.visibility)
                }
            }
            topicList.each {
                topic ->
                    createdTopics.add(new TopicVO(id: topic.id, name: topic.topicName,
                            visibility: topic.visibility, createdBy: topic.createdBy))
            }
        }
        return createdTopics
    }


    Topic saveTopic(TopicCO topicCO) {
        Topic topic = topicCO.getTopic()
        if (topic) {
            if (topicCO.newName) {
                topic.topicName = topicCO.newName
            } else {
                topic.visibility = Visibility.convertIntoEnum(topicCO.visibility)
            }
            return saveTopic(topic)
        }
        return null
    }

/*

    def delete(Long id, User user) {

        Topic topic = Topic.findByIdAndCreatedBy(id,user)

// log.error("${topic.topicname}")

        if (user && topic )
        {
            topic.delete(flush: true)

            return flash.message = "topic deleted"

        }
        else
        {
            return flash.error = "could not delete topic"
        }

    }

    @Transactional
    def saveAndSubscribe(Topic topic) {
        topic.save flush: true
        subscribe(topic)

    }

    Subscription subscribe(Topic topic) {
        User createdBy = topic.createdBy
        new Subscription(topic: topic, user: createdBy, seriousness: Seriousness.CASUAL).save flush: true
        //transactionStatus.setRollbackOnly()
    }

*/

    Subscription joinTopic(Topic topic, User user) {
        if (topic && user) {
            return subscriptionService.saveSubscription(new Subscription(user: user, topic: topic))
        }
        return null
    }



}


