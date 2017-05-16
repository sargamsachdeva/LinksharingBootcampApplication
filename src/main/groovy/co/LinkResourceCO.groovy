package co

import com.ttn.linksharingbootcamp.User
import grails.validation.Validateable

class LinkResourceCO implements Validateable{

    String description
    User createdBy
    Long topic
    String url
    static constraints = {
        description(blank: false)
        url(blank: false, url: true)
    }


}
