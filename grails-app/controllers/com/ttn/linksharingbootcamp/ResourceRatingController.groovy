package com.ttn.linksharingbootcamp

import grails.converters.JSON

class ResourceRatingController {

    def resourceService

    def save(Long id, Integer score) {

        if (session.user) {
            Resource resource = Resource?.get(id)
            User user = session.user
            log.warn("resource-->${resource}")
            log.warn("user-->${user}")

            if (user && resource) {
                if (resourceService.saveRating(resource, user, score)) {
                    render flash.message = g.message(code: "com.ttn.linksharing.resource.rating.rating.saved")
                } else {
                    render flash.error = g.message(code: "com.ttn.linksharing.resource.rating.rating.not.saved")
                }
            } else {
                render flash.error = g.message(code: "com.ttn.linksharing.resource.rating.resource.user.not.set")
            }
        }
    }
}
