package co

import grails.validation.Validateable

//@Validateable
class UserCO {
    Long id
    String userName
    String password
    String firstName
    String lastName
    String email
    String photo
    Boolean isAdmin = false
    String confirmPassword

    static constraints = {

        email(unique: true, email: true ,blank: false)

        userName(unique: true, blank: false)

        password(minSize: 5, blank: false,

                validator: {password, obj ->
                    def password2 = obj.confirmPassword
                    password2 == password ? true : ['password.mismatch']
                })

        firstName(blank: false)

        lastName(blank: false)

        photo(nullable: true)

        confirmPassword(nullable: true, blank: true)
    }

}
