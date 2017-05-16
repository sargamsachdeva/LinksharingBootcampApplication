package com.ttn.linksharingbootcamp

import com.ttn.linksharingbootcamp.User
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
//@TestFor(UserController)
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "testsomething"() {
        expect:
            true
    }

    def "CheckIndexActionOfUserController"() {
        setup:
        session.user = new User() //User.findByUserName("admin")

        when:
        controller.index()

        then:
         "session ${session.user}"
         "username ${u.userName}"
    }

}
