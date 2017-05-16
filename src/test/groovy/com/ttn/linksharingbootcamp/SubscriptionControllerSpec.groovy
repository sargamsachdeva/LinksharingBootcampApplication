package com.ttn.linksharingbootcamp

import constants.Constants
import enums.Visibility
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SubscriptionController)
@Mock([Topic, User, Subscription])
class SubscriptionControllerSpec extends Specification {

    def "testSaveWhenTopicNotFound"() {

        when:
        controller.save(1.toLong())

        then:
        flash.error == "Topic Does not Exist"
    }

    def "testSaveWhenTopicFound"() {
        given:
        User user = new User(firstName: "sar", lastName: "Gupta", userName: "sar123",
                email: "sar1@yahoo.co.in", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        user.save(flush: true)

        User user1 = new User(firstName: "Garima", lastName: "Jain", userName: "garimajain2707",
                email: "garima.jain2707@gmail.com", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        session.user = user1
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        user.addToTopics(topic)
        topic.save(flush: true)

        when:
        controller.save(topic.id)
        then:
        flash.message == "Subscription saved successfully"
    }

    def "testUpdateWhenUserAndTopicDoesNotMatchInSubscription"() {

        given:
        User user = new User(firstName: "sargam", lastName: "Gupta", userName: "sar123",
                email: "sar23@yahoo.co.in", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes, confirmPassword: Constants.PASSWORD)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        User user1 = new User(firstName: "Garima", lastName: "Jain", userName: "garimajain2707",
                email: "garima.jain2707@gmail.com", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        session['user'] = user1

        when:
        controller.update(topic.id, "SERIOUS")

        then:
        flash.error == "Subscription not found"
    }

    def "testUpdateWhenUserAndTopicMatchInSubscription"() {
        given:

        User user = new User(firstName: "sargam", lastName: "Gupta", userName: "sar23",
                email: "sar1@yahoo.co.in", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        session['user'] = user

        when:
        controller.update(topic.id, "SERIOUS")

        then:
        flash.message == "Subscription saved successfully"
    }


    def "testDeleteWhenTopicAndUserDoesNotMatchInSubscription"() {
        given:
        User user = new User(firstName: "sar", lastName: "Gupta", userName: "sar123",
                email: "sar@yahoo.co.in", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        User user1 = new User(firstName: "Garima", lastName: "Jain", userName: "garimajain2707",
                email: "garima.jain2707@gmail.com", password: Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword: Constants.PASSWORD)
        session['user'] = user1

        when:
        controller.delete(topic.id)

        then:
        flash.error == "Subscription not found"
    }

    def "testDeleteWhenTopicAndUserMatchInSubscription"() {
        given:
        User user = new User(firstName: "sar", lastName: "Gupta", userName: "sar123",
                email: "sar123@yahoo.co.in", password:  Constants.PASSWORD,
                admin: false, active: true, photo: "abc".bytes,
                confirmPassword:  Constants.PASSWORD)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        session['user'] = user

        when:
        controller.delete(topic.id)

        then:
        flash.message == "Subscription deleted"
    }
}