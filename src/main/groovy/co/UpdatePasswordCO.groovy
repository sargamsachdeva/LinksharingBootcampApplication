package co

import com.ttn.linksharingbootcamp.User
import grails.validation.Validateable

class UpdatePasswordCO implements Validateable {
    Long id
    String oldPassword
    String password
    String confirmPassword



    static constraints = {

        confirmPassword(nullable: true, blank: true)

        password(nullable: false, blank: false, minSize: 5)

        confirmPassword(bindable: true,

                validator: { password1, obj ->
                    def password2 = obj.password
                    password2 == password1 ? true : false

                })
    }


    User getUser() {

        User user= User.get(id)
        if(oldPassword==user.password)
            return user
        else
            return null
    }
}
