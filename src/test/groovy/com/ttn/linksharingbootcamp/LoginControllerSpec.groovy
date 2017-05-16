package com.ttn.linksharingbootcamp

import constants.Constants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
@Mock([User])
class LoginControllerSpec extends Specification {

    def "CheckIndexActionIfUser'sSessionIsSetThenRequestIsForwardToUserIndexAction"() {
        setup:
        session.user = new User() //User.findByUserName("admin")

        when:
        controller.index()

        then:
        response.forwardedUrl == "/user/index"
    }

    def "CheckIndexActionIfUser'ssSssionisnotSetThenErrorShouldbeRendered"() {
        when:
        controller.index()

        then:
        response.status==404
    }

    def "CheckLogoutUser'sSessionIsInvalidatedThenRedirectToLoginIndexAction"() {
        when:
        controller.logout()

        then:
        session.user == null
        response.redirectedUrl == "/login/index"
    }


    def "CheckLoginHandlerUserIsAbleToLogin"() {
        setup:
        User user = new User(userName: "sargam1" ,firstName: "sargam", lastName: "sachdeva", email: "sargam.sachdeva@tothenew.com",
                password: Constants.PASSWORD,active: true,admin: true)
        user.save(flush: true)

        when:
        controller.login(user.userName, user.password)

        then:
        response.redirectedUrl == '/login/index'
    }

    def "CheckLoginHandlerUserIsNotActive"() {
        setup:
        User user = new User(userName: "userNew", password: Constants.PASSWORD,
                firstName: "Name", lastName: "Lname", email: "user@gmail.com",active: false)
        user.save(flush: true)

        when:
        controller.login(user.userName, user.password)

        then:
        flash.error == "User is not active"
    }

    def "CheckLoginHandlerUserIsNotFound"() {
        setup:
        User user = new User(userName: "aa", password: Constants.PASSWORD,
                firstName: "Name", lastName: "Lname", email: "aa@gmail.com")

        when:
        controller.login(user.userName, user.password)

        then:
        flash.error == "User not found"
    }

}