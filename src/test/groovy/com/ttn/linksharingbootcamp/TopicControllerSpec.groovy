package com.ttn.linksharingbootcamp

import constants.Constants
import enums.Visibility
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TopicController)
@Mock([User, Topic, Subscription])
class TopicControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "testSomething"() {
        expect:
            true
    }

    def "checkTopicShow"() {
        setup:
        User user = new User(userName: "user5", active: true, password: Constants.PASSWORD
                , firstName: "Name", lastName: "Lname", email: "user5@ttnd.com")

        user.save(flush:true)

        Topic topic = new Topic(topicName:'groovy', visibility: visible, createdBy: user)

        session['user'] = user

        topic.save(flush: true)

        when:
        println("topic-id :"+topic.id)
        controller.show(topic.id)


        then:
        response.text == result

        where:
        visible            | result
        Visibility.PUBLIC  | "success public"
        Visibility.PRIVATE | "success private"
    }

    def "CheckTopicShow_User_not_subscribed_to_private_topic"() {
        setup:
        User user = new User(username: "user5", active: true, password: Constants.PASSWORD, firstname: "Name", lastname: "Lname", email: "user5@ttnd.com")

        User user1 = new User(username: "user6", active: true, password: Constants.PASSWORD,firstname: "Name", lastname: "Lname", email: "user6@ttnd.com")
        Topic topic = new Topic(topicname: 'groovy', visibility: Visibility.PRIVATE, createdBy: user)

        user.save(flush: true)
        session['user']= user1
        topic.save(flush: true)

        when:
        controller.show(topic.id)

        then:
        response.redirectedUrl == "/login/index"
    }

    def "checkTopicSave"() {

        setup:
        User user = new User(userName: "user5", active: true, password: Constants.PASSWORD
                , firstName: "Name", lastName: "Lname", email: "user5@ttnd.com")

        user.save(flush:true)

        String topicName='groovy'
        String visibility=Visibility.convertIntoEnum("public")
        Topic topic = new Topic(topicName:topicName, visibility: visibility, createdBy: user)
        session['user'] = user
        topic.save(flush:true)

        when:
        controller.save(topicName,visibility)

        then:
        response.text == "success"
    }

}
