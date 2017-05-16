package co

import com.ttn.linksharingbootcamp.User
import enums.Visibility
import grails.validation.Validateable

class TopicSearchCO extends SearchCO implements Validateable {

    Long id
    Visibility visibility

    User getUser() {
        return User.get(id)
    }

}
