package vo

import com.ttn.linksharingbootcamp.Subscription
import com.ttn.linksharingbootcamp.User
import enums.Seriousness
import enums.Visibility

class TopicVO {
    Long id
    String topicName
    Visibility visibility
    Seriousness topicSeriousness
    //Subscription subscription
    Integer count
    User createdBy


    String toString() {
        return "${topicName}"
    }
}
