package co

import com.ttn.linksharingbootcamp.Topic
import com.ttn.linksharingbootcamp.User
//import grails.validation.Validateable

//@Validateable
class TopicCO {
    Long topicId
    String topicName
    String visibility
    User createdBy
    String newName

    static constraints = {
        topicId(nullable: true, blank: true)
        topicName(nullable: true, blank: true)
        visibility(nullable: true, blank: true)
        newName(nullable: true, blank: true)
        createdBy(nullable: true, blank: true)
    }

    Topic getTopic() {
        return Topic.findOrCreateByTopicNameAndCreatedBy(topicName, createdBy)
    }
}
