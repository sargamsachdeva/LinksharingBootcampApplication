package com.ttn.linksharingbootcamp

import enums.Seriousness

class Subscription {

    Topic topic
    Seriousness seriousness
    Date dateCreated
    Date lastUpdated

    static constraints = {

        topic(unique: ['user'])
    }

    static mapping = {

        topic lazy:false
        user lazy:false
    }

    static belongsTo = [user:User,
                        topic:Topic]

    String toString() {

        return "${user} subscribed ${topic}"
    }
}
