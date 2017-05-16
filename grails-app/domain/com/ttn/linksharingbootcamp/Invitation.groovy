package com.ttn.linksharingbootcamp

import java.sql.Timestamp

class Invitation {

    String invited
    User invitee
    String urlHash
    Topic topic
    Timestamp created;

    static hasMany = [invitee: User, topic: Topic]

    static mapping = {
        urlHash(sqlType: 'text')
        created(sqlType: 'timestamp', defaultValue: "now()")
    }

    static constraints = {
        urlHash(unique: true, blank: false)
        invitee(blank: false)
        topic(blank: false)
        invited(blank: false, unique: ['invitee', 'topic'])
        created(nullable: true)
    }
}
