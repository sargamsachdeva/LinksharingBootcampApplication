package com.ttn.linksharingbootcamp

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import enums.Visibility

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Topic)
@Mock([Subscription])
class TopicSpec extends Specification {

    @Unroll("Topic Constraints ----- #sno")
    void  "testTopicValidations"() {

        given:
        Topic topic = new Topic(topicName: topicName, createdBy: createdBy, visibility: visibility)

        when:
        Boolean result = topic.validate()

        then:
        result == valid

        where:
        sno | topicName | createdBy    | visibility       | valid
        1   | "grails"  | new User() | Visibility.PRIVATE | true
        2   | " "       | new User() | Visibility.PRIVATE | false
        3   | null      | new User() | Visibility.PRIVATE | false
        4   | "grails"  | null       | Visibility.PUBLIC  | false
        5   | "grails"  | new User() | null               | false
    }

    def "topicUserUniqueness"() {

        given:
        String topicName = "grails"
        User creator = new User()
        Visibility visibility = Visibility.PRIVATE
        Topic topic = new Topic(topicName: topicName, createdBy: creator, visibility: visibility)

        when:
        topic.save()

        then:
        Topic.count() == 1

        when:
        Topic newTopic = new Topic(topicName: topicName, createdBy: creator, visibility: visibility)
        newTopic.save()

        then:
        Topic.count() == 1
        newTopic.errors.allErrors.size() == 1
        newTopic.errors.getFieldErrorCount('topicName') == 1
    }

    def "toStringCheck"() {

        given:
        User user = new User(userName: 'sargam')
        Topic topic = new Topic(topicName: name, createdBy: user)

        when:
        String topicName = topic.toString()

        then:
        topicName == result

        where:
        name            | result
        "testTopicName" | "testTopicName, sargam"
    }
}