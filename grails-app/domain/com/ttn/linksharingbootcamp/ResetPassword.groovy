package com.ttn.linksharingbootcamp

import java.sql.Timestamp

class ResetPassword {

    String email
    String urlHash
    Timestamp created;

    static mapping = {
        created(sqlType: 'timestamp', defaultValue: "now()")
    }

    static constraints = {
        urlHash(unique: true, blank: false)
        email(blank: false, unique: true)
        created(nullable: true)
    }
}