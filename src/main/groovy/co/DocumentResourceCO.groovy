package co

import com.ttn.linksharingbootcamp.User
import grails.validation.Validateable


class DocumentResourceCO  implements Validateable{



    String description
    User createdBy
    Long topic

    String fileName

    static constraints = {

        description(blank: false)
        fileName( nullable: true, blank: true)

    }
}
