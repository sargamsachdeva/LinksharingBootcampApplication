package com.ttn.linksharingbootcamp

import co.InviteCO
import co.ResourceSearchCO
import co.SearchCO
import co.TopicSearchCO
import co.UpdatePasswordCO
import co.UpdateProfileCO
import co.UserCO
import co.UserSearchCO
import constants.Constants
import enums.Seriousness
import enums.Visibility
import util.EncryptUtils
import vo.PostVO
import vo.TopicVO
import vo.UserVO
import vo.conversion.DomainToVO

class UserController {

    def userService
    def mailService

    def index(SearchCO searchCO) {
        searchCO.max = searchCO.max ?: 5
        searchCO.offset = searchCO.offset ?: 0

        List<TopicVO> trendingTopicsVO = DomainToVO.trendingTopics()
        UserVO userDetailsVO = DomainToVO.userDetails(session.user)
        List<PostVO> recentPostVO = DomainToVO.recentPosts()
        List<PostVO> readingItemsVO = DomainToVO.readingItems(session.user, searchCO)

        render(view: 'index', model: [subscribedTopics : session.user.subscribedTopics,
                                      trendingTopics   : trendingTopicsVO,
                                      userDetails      : userDetailsVO,
                                      recentPosts      : recentPostVO,
                                      readingItems     : readingItemsVO,
                                      subscriptions    : DomainToVO.userSubscriptions(session.user),
                                      searchCO         : searchCO,
                                      totalReadingItems: session.user.getTotalReadingItem()])
    }

    def profile(ResourceSearchCO resourceSearchCO) {

        resourceSearchCO.max = resourceSearchCO.max ?: 5
        resourceSearchCO.offset = resourceSearchCO.offset ?: 0
        User user = User.get(resourceSearchCO.id)
        log.info("asd/fjaklsdfajkls")
        log.info("${resourceSearchCO.id}")
        if (session.user) {
            if (!(User.isUserAdmin(session.user) || User.isCurrentUser(session.user, User.load(resourceSearchCO.id)))) {
                resourceSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            resourceSearchCO.visibility = Visibility.PUBLIC
        }
        render view: 'profile', model: [userDetails     : DomainToVO.userDetails(user),
                                        createdResources: DomainToVO.createdResources(resourceSearchCO),
                                        totalPosts      : user.getPostsCount(),
                                        resourceSearchCO: resourceSearchCO,
                                        topicList       : user.getSubscribedTopics(),
                                        userTopics      : Topic.findAllByCreatedBy(session.user)]
    }

    def toggleUserActiveStatus(Long id) {

        User admin = session.user
        User normal = User.get(id)
        if (admin && normal) {
            User tempUser = userService.toggleActiveStatus(admin, normal)

            if (tempUser) {
                flash.message = g.message(code: "com.ttn.linksharing.user.update.User.Active.Status.active.status.toggled")
            } else {
                flash.error = g.message(code: "com.ttn.linksharing.user.update.User.Active.Status.active.status.not.toggled")
            }
        }
    }

    def showEditProfile() {
        UserVO userDetailsVO = DomainToVO.userDetails(session.user)
        render view: 'edit', model: [userDetails: userDetailsVO,
                                     userTopics : Topic.findAllByCreatedBy(session.user)]
    }

    def updatePassword(UpdatePasswordCO updatePasswordCO) {

        if (session.user) {
            updatePasswordCO.id = session.user.id

            if (!updatePasswordCO.validate()) {
                flash.message = "invalid"

            } else {
                User user = userService.updatePassword(updatePasswordCO)
                if (user) {
                    session.user = user

                    flash.message = g.message(code: "com.ttn.linksharing.user.save.password.updated")

                } else {
                    log.info("..inside ")
                    flash.error = g.message(code: "com.ttn.linksharing.user.save.password.not.updated")
                }
            }
        } else {
            flash.error = "User not set"
        }

        redirect(url: request.getHeader("referer"))
    }


    def save(UpdateProfileCO updateProfileCO) {

        if (session.user) {
            updateProfileCO.id = session.user.id
            updateProfileCO.file = params.file
            log.info("params.file--->>>> ${params.file}")

            if (updateProfileCO.hasErrors()) {
                render updateProfileCO.errors.allErrors

            } else {
                User user = userService.updateProfile(updateProfileCO)
                if (user) {
                    session.user = user
                    log.warn("session-user---->${session.user}")
                    flash.message = g.message(code: "com.ttn.linksharing.user.save.profile.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(user),
                                                 userCo     : user])
                } else {
                    flash.error = g.message(code: "com.ttn.linksharing.user.save.profile.not.updated")
                    render(view: 'edit', model: [userDetails: DomainToVO.userDetails(session.user),
                                                 userCo     : session.user])
                }
            }
        }
    }

    def topics(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(User.isUserAdmin(session.user) || User.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<TopicVO> createdTopics = DomainToVO.createdTopics(topicSearchCO)
        render(template: '/topic/list', model: [topicList: createdTopics])
    }

    def subscriptions(Long id) {
        TopicSearchCO topicSearchCO = new TopicSearchCO(id: id)
        if (session.user) {
            if (!(User.isUserAdmin(session.user) || User.isCurrentUser(session.user, User.load(id)))) {
                topicSearchCO.visibility = Visibility.PUBLIC
            }
        } else {
            topicSearchCO.visibility = Visibility.PUBLIC
        }
        List<Topic> subscribedTopics = DomainToVO.subscribedTopics(topicSearchCO)
        render(template: '/topic/list', model: [topicList: subscribedTopics])
    }

    def registeredUsers(UserSearchCO userSearchCO) {
        userSearchCO.max = userSearchCO.max ?: 20
        userSearchCO.offset = userSearchCO.offset ?: 0
        List<UserVO> registeredUsers = DomainToVO.registeredUsers(userSearchCO, session.user)
        if (registeredUsers) {
            render(view: 'users', model: [userList    : registeredUsers,
                                          totalUsers  : User.countByAdmin(false) ?: 0,
                                          userSearchCO: userSearchCO])
        } else {
            redirect(url: request.getHeader("referer"))
        }
    }

    def updateUserActiveStatus(Long id) {
        User admin = session.user
        User normal = User.get(id)
        if (admin && normal) {
            User tempUser = userService.toggleActiveStatus(admin, normal)
            if (tempUser) {
                flash.message = g.message(code: "com.ttn.linksharing.user.update.User.Active.Status.active.status.toggled")
            } else {
                flash.error = g.message(code: "com.ttn.linksharing.user.update.User.Active.Status.active.status.not.toggled")
            }
        }
        redirect(url: request.getHeader("referer"))
    }

    def invite(String code) {
        code = URLDecoder.decode(code, "UTF-8")
        log.info "$code"
        if (code) {
            Invitation invitation = Invitation.findByUrlHash(code)
            log.info "$invitation"
            if (invitation) {
                Topic topic = invitation.topic
                User user = User.findByEmail(invitation.invited)
                log.info "user is $user ${invitation.invited}"
                Subscription subscription = new Subscription(user: user, topic: topic, seriousness: Seriousness.CASUAL)
                if (subscription.save(flush: true)) {
                    invitation.delete()
                    flash.message = "Subscription saved successfully"
                } else {
                    flash.error = subscription.errors.allErrors.collect { message(error: it) }.join(", ")
                }
            } else {
                flash.error = "invalid invitation request, invitation doesnot exist"
            }
        }
        redirect(controller: 'login', action: 'index')
    }

    def sendInvite(InviteCO inviteCO) {
        log.info("$inviteCO")
        User user = User.findByEmail(inviteCO.email)
        if(user){
            Subscription subscription = Subscription.findByUserAndTopic(user, Topic.load(inviteCO.topic))
            if(!subscription){

                String hashed = EncryptUtils.encryptSHA256("${session.user}${inviteCO.email}${inviteCO.topic}${Constants.SALT}" as String)

                Invitation invitation = new Invitation(invitee: session.user, invited: inviteCO.email, topic: inviteCO.topic, urlHash: hashed)
                if(invitation.validate()){
                    String text1 = createLink(controller: 'user', action: 'invite', params: [code: hashed], absolute: true)
                    log.info text1
                    runAsync {
                        mailService.sendMail {
                            to inviteCO.email
                            from "csi.online2016@gmail.com"
                            subject 'linksharing: invitation'
                            text text1
                        }
                    }
                    invitation.save(flush: true)
                    flash.message = "Invite sent successfully"
                } else{
                    flash.error = invitation.errors.allErrors.join(', ')
                }
            } else{
                flash.error = "user already subscribed to that topic"
            }
        } else{
            flash.error = "invitation not sent, user doesn't exists with given email"
        }


        redirect(url: request.getHeader("referer"))
    }

    def image(Long id) {

        User user = User.get(id)
        if (user) {
            Byte[] userPhoto = userService.image(user)
            if (userPhoto) {


                OutputStream out = response.getOutputStream()
                out.write(userPhoto)
                out.flush()
                out.close()

            }
        } else {
            render flash.error = g.message(code: "com.ttn.linksharing.user.image.image.not.found")
        }
    }
}

