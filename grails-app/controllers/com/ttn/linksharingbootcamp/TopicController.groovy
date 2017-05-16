package com.ttn.linksharingbootcamp

import co.TopicCO
import enums.Seriousness
import enums.Visibility
import vo.PostVO
import vo.TopicVO
import vo.UserVO
import vo.conversion.DomainToVO

class TopicController {

    def topicService
    def emailService

    def show(Long id) {
        Topic topic = Topic?.read(id)
        if (topic) {

            TopicVO topicDetailsVO = DomainToVO.topicDetails(topic)
            List<UserVO> subscribedUsersVO = DomainToVO.subscribedUsers(topic)
            List<PostVO> topicPostsVO = DomainToVO.topicPosts(topic)

            if (topic.visibility == Visibility.PUBLIC) {
                render(view: 'show', model: [topicDetails: topicDetailsVO, subscribedUsers: subscribedUsersVO,
                                             topicPosts  : topicPostsVO])
            } else if (topic.visibility == Visibility.PRIVATE) {
                if (Subscription?.findByUserAndTopic(session.user, topic)) {
                    render(view: 'show', model: [topicDetails: topicDetailsVO, subscribedUsers: subscribedUsersVO,
                                                 topicPosts  : topicPostsVO])
                } else {
                    flash.error = g.message(code: "com.ttn.topic.show.cannot.access.private.topic")
                }
            }
        } else {

            flash.error = g.message(code: "com.ttn.topic.show.topic.not.exist")
        }
    }

    def delete(Long id)
    {
        User user = session['user']
        Topic topic = Topic.load(id)
        List<Invitation> invitationList = Invitation.findAllByTopic(topic)

        if(invitationList)
        { invitationList.each {it.delete(flush: true)}}
        else{
            log.info("cannot invite here...........")
        }

        if (user && topic )
        {
            topic.delete(flush: true)

            flash.message = "topic deleted"

        }
        else
        { flash.error = g.message(code:"com.ttn.linksharing.topic.delete.topic.not.deleted")
        }

        redirect(url: request.getHeader("referer"))
    }

    def save(String topicname, User createdBy, String visibility){

        Topic topic = Topic.findByTopicNameAndCreatedBy(topicname,session.user)
        if (topic)
        {
            flash.error="Topic Already Exists"
        }
        else {
            topic = new Topic(topicName: topicname, createdBy: session.user, visibility: Visibility.convertIntoEnum(visibility))


            if (topic) {
                if(topic.validate()){

                    if(topic.save(flush:true))
                        flash.message=g.message(code: "com.ttn.linksharing.topic.topic.saved.updated")
                    else
                        flash.error=g.message(code: "com.ttn.linksharing.topic.topic.not.saved.updated")
                }
                else{
                    flash.error = g.message(code:"com.ttn.linksharing.topic.topic.save.update.error" )
                }
            } else{
                flash.error = "user not set"
            }
        }


        redirect(controller: 'user', action: 'index')
    }

    def invite(Long topic, String email) {
        if (topic && email) {
            if (emailService.invite(topic, email)) {
                flash.message = g.message(code: "com.ttn.linksharing.topic.invite.invitation.sent")
            } else {
                flash.error = g.message(code: "com.ttn.linksharing.topic.invite.invitation.not.sent")
            }
        }
        redirect(url: request.getHeader("referer"))
    }

    def join(Long id) {

        Topic topic = Topic.get(id)
        if (topic) {
            if (session.user) {
                if (topicService.joinTopic(topic, session.user)) {
                    flash.message = g.message(code: "com.ttn.linksharing.topic.join.topic.subscribed")
                } else {
                    flash.error = g.message(code: "com.ttn.linksharing.topic.join.topic.not.subscribed")
                }
            } else {
                flash.error = g.message(code: "com.ttn.linksharing.topic.join.session.not.set")
            }
        } else {
            flash.error = g.message(code: "com.ttn.linksharing.topic.join.topic.do.not.exists")
        }
        redirect(url: request.getHeader("referer"))
    }

    def edit( Long id, String topicname)
    {
        Topic topic= Topic.get(id)

        if(session.user&&topic)
        {
            topic.topicName=topicname

        }

        log.info(".......in edit..>>>"+topic.id)
        if(topic.save(flush:true))
        {
            flash.message="topic updated successfully"
        }

        else {
            flash.error="topic cannot be updated"
        }
        redirect(url:request.getHeader('referer'))

    }

}
