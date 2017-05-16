package com.ttn.linksharingbootcamp

class ResourceRating {

    Resource resource
    User user
    Integer score
    Date dateCreated
    Date lastUpdated

    static constraints = {

        score(min: 1, max: 5)
        resource(unique: ['user'])
    }

    static belongsTo = [user:User,
                        resource:Resource]


    String toString() {
        return "${user} rated ${resource} by ${score}"
    }
}
