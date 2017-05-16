package com.ttn.linksharingbootcamp

import constants.Constants
import enums.Seriousness
import enums.Visibility
import grails.converters.JSON

class SubscriptionController {

    def subscriptionService

    def save(Long userId, Long topicId) {

        User user = User?.get(userId)
        Topic topic = Topic?.get(topicId)
        if (user && topic) {

            if (subscriptionService.saveSubscription(topic, user)) {
                flash.message = g.message(code: "com.ttn.linksharing.subscription.save.subscription.saved")
            } else {
                flash.error = g.message(code: "com.ttn.linksharing.subscription.save.subscription.not.saved")
            }
        } else {
            flash.error = g.message(code: "com.ttn.linksharing.subscription.save.topic.user.not.set")
        }
        redirect(url: request.getHeader("referer"))
    }





    def delete(Long userId, Long topicId) {
        User user = User?.get(userId)
        Topic topic = Topic?.get(topicId)
        if (user && topic) {
            if (User.ifTopicIsCreatedBy(topic, user)) {
                flash.error = g.message(code: "com.ttn.linksharing.subscription.delete.user.cannot.unsubscribe.created.topic")
            } else {
                if (subscriptionService.deleteSubscription(topic, user)) {
                    flash.message = g.message(code: "com.ttn.linksharing.subscription.delete.subscription.deleted")
                } else {
                    flash.error = g.message(code: "com.ttn.linksharing.subscription.delete.subscription.not.deleted")
                }
            }
        } else {
            flash.error = g.message(code: "com.ttn.linksharing.subscription.delete.topic.user.not.set")
        }
        redirect(url: request.getHeader("referer"))
    }

  /*  def updateSeriousness(Long sid, String seriousness){
        log.info "$sid"
        log.info "updateSeriousness $seriousness"
        if(session.user){
            Subscription subscription = Subscription.load(sid)
            subscription.seriousness = Seriousness.checkSeriousness(seriousness)
            if(subscription.save(flush: true)){
                flash.message = "seriousness updated"
            } else{
                flash.error = subscription.errors.allErrors.join(", ")
            }
        } else{
            flash.error = "user not logged in"
        }
        redirect(url: request.getHeader("referer"))
    }

*/
    def update2(Long topicId,String seriousness) {

        User user = session['user']
        Topic topic = Topic?.get(topicId)

        log.info("userinupdate2-->${user}")
        log.info("topicin update2-->${topic}")

        if (user && topic) {
            Subscription subscription = Subscription?.findByUserAndTopic(user, topic)
            log.info("seriousness in update2-->${subscription.seriousness} serious-->${seriousness}")
            subscription.seriousness = Seriousness.checkSeriousness(seriousness)

            log.info("subs in update2-->${subscription}")
            if (subscription) {
                if (subscriptionService.saveSubscription(subscription,user)) {
                    flash.message = "updated"
                } else {
                    flash.error = "not updated"
                }
            } else {
                flash.error = "not set"
            }
        } else {
            flash.error = "user not set"
        }
        redirect(url:request.getHeader("referer"))
    }

    def updateSeriousness(Long tid, String seriousness)
    {

        Subscription subscription = Subscription.findByTopicAndUser(Topic.get(tid),session.user)

        if(session['user'])
        {
            if(subscription&&subscription.seriousness!=Seriousness.checkSeriousness(seriousness))
            {
                subscription.seriousness=Seriousness.checkSeriousness(seriousness)

                if(subscription.save(flush:true))
                    flash.message = "Seriousness updated"


                else
                    flash.error= "Subscription cannot be set"

            }
            else
            {
                flash.error ="Subscription not found or already ${seriousness}"
            }

        }
        else {

            flash.error= 'User not set'
        }

        redirect(url:request.getHeader('referer'))
    }
}


