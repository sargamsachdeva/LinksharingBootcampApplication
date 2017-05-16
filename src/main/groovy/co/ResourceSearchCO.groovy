package co

import com.ttn.linksharingbootcamp.User
import enums.Visibility

class ResourceSearchCO extends SearchCO {

    Long topicId
    Visibility visibility
    Long id
    Boolean global

    User getUser() {
        return User.get(id)
    }
}
