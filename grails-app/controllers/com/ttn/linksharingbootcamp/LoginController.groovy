package com.ttn.linksharingbootcamp

import co.UserCO
import constants.Constants
import util.EncryptUtils
import vo.PostVO
import vo.conversion.DomainToVO


class LoginController {

    def userService
    def mailService

    def index() {
        if (session.user) {

            forward(controller: 'user', action: 'index')
            render flash.message = "Logged in"
        } else {
            render(view: 'index')
        }
    }

    def logout() {
        session.invalidate()
        redirect(action: 'index')
    }

    def login(String loginUserName, String loginPassword) {

        User user = User.findByUserNameAndPassword(loginUserName, loginPassword)
        if (user) {

            if (user.active) {
                session.user = user
            } else {
                flash.error = g.message(code: "com.ttn.login.login.user.not.active")
            }
        } else {
            session.invalidate()
            flash.error = g.message(code: "com.ttn.login.login.user.not.found")
        }

        redirect(controller: 'login', action: 'index')
    }

    def registration(UserCO userCo) {

        def file = params.file
        User user = userService.registerUser(userCo, file)

        if (user) {
            flash.message = "user.registered.successfully"
            session['user'] = user
        } else {
            flash.message = "user.could.not.register"
        }


        redirect(controller: 'login', action: 'index')
    }




    def forgotPassword() {
        render view: '/auth/forgotPassword'
    }

    def reset(String code) {
        code = URLDecoder.decode(code, "UTF-8")
        log.info "$code"
        if(code){
            ResetPassword resetPassword = ResetPassword.findByUrlHash(code)
            log.info "$resetPassword"
            if(resetPassword){
                User user = User.findByEmail(resetPassword.email)
                log.info "user is $user ${resetPassword.email}"
                if(user){
                    log.info "rendering view resetpassword"
                    render view: '/auth/resetPassword', model: [code: code]
                    return
                } else {
                    flash.error = "invalid request"
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }
        render view: '/index'
    }

    def resetPasswordProcess(String code, String password, String confirmPassword) {
        if(code){
            ResetPassword resetPassword = ResetPassword.findByUrlHash(code)
            log.info "$resetPassword"
            if(resetPassword){
                User user = User.findByEmail(resetPassword.email)
                log.info "user is $user ${resetPassword.email}"
                if(user){
                    if(password.equals(confirmPassword)){
                        user.password = password
                        user.confirmPassword = confirmPassword
                        if(user.save(flush: true)){
                            flash.message = "Password has been reset"
                        } else{
                            flash.error = user.errors.allErrors.join(", ")
                            redirect(url: request.getHeader("referer"))
                            return
                        }
                        redirect(url: '/login')
                    } else {
                        flash.error = "passwords are not equal"
                        redirect(url: request.getHeader("referer"))
                    }
                } else {
                    flash.error = "invalid request"
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }

        render view: '/index'
    }

    def resetPassword(String email){
        User user = User.findByEmail(email)
        if(email){
            if(user) {
                String hashed = EncryptUtils.encryptSHA256("${email}${Constants.SALT}" as String)
                ResetPassword rpass = new ResetPassword(email: email, urlHash: hashed)
                if(rpass.validate()){
                    String text1 = createLink(controller: 'login', action: 'reset', params: [code: hashed], absolute: true)
                    log.info text1
                    mailService.sendMail {
                        to email
                        from "csi.online2016@gmail.com"
                        subject 'linksharing: reset password'
                        text text1
                    }
                    rpass.save(flush: true)
                    flash.message = "a mail has been sent to the provided email Id containing the link to reset password"
                } else{
                    flash.error = rpass.errors.allErrors.join(", ")
                }
            } else {
                flash.error = "No user exists with email id: $email"
            }
        } else {
            flash.error = "please enter an email"
        }
        redirect(url: request.getHeader("referer"))
    }
}