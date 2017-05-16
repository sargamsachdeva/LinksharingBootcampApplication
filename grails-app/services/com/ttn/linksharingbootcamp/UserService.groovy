package com.ttn.linksharingbootcamp

import co.UpdatePasswordCO
import co.UpdateProfileCO
import co.UserCO
import grails.transaction.Transactional
import grails.util.GrailsUtil
import grails.web.context.ServletContextHolder
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class UserService {

    def assetResourceLocator
    def grailsApplication

    User saveUser(User user) {
        if (user.validate()) {


            return user.save(flush: true)
        }

        log.info("errors-----> ${user.errors.allErrors}")
        return null
    }

    User toggleActiveStatus(User admin, User normal) {

        if (admin && normal) {
            if (User.isUserAdmin(admin) && !User.isUserAdmin(normal) && User.isUserActive(admin)) {
                normal.confirmPassword=normal.password
                normal.active = !normal.active
                return saveUser(normal)
            }
        }
        return null

    }

    User updatePassword(UpdatePasswordCO updatePasswordCO) {

        User user = updatePasswordCO.getUser()
        if (user) {
            user.password = updatePasswordCO.password
            user.confirmPassword = updatePasswordCO.password
            return saveUser(user)
        }
        return null
    }


    User registerUser(UserCO userCO, def sourceFile) {

        String filePath = "/home/sargam/Documents/LinkSharingBootcampPractice/grails-app/assets/images/grails_user_image/${UUID.randomUUID()}.jpeg"
        User user = new User(firstName: userCO.firstName, lastName: userCO.lastName,
                email: userCO.email, userName: userCO.userName,
                password: userCO.password,
                confirmPassword: userCO.confirmPassword,
                admin: userCO.isAdmin, active: true)


        if (sourceFile.empty) {

            if (user.save(flush: true)) {
                return user
            } else {
                return null
            }

        } else {

            user.photo = filePath

            def destinationFile = new File(filePath)

            if (destinationFile) {

                sourceFile.transferTo(destinationFile)
            }

            if (user.save(flush: true)) {
                return user
            } else {
                return null
            }

        }
    }


    User updateProfile(UpdateProfileCO updateProfileCO) {

        String filePath = "/home/sargam/Documents/LinkSharingBootcampPractice/grails-app/assets/images/grails_user_image/${UUID.randomUUID()}.jpeg"

        User user = updateProfileCO.getUser()


        log.warn("User------> ${user}")
        log.warn("user password :" + user.password)
        user.confirmPassword = user.password
        log.warn("user password :" + user.confirmPassword)
        if (user) {
            if (!updateProfileCO.file.empty) {
                user.photo = filePath
            }

            def destinationFile = new File(filePath)

            if (destinationFile) {

                updateProfileCO.file.transferTo(destinationFile)
            }
            user.firstName = updateProfileCO.firstName
            user.lastName = updateProfileCO.lastName

            log.warn("firstname--> ${user.firstName}")
            log.warn("lastname--> ${user.lastName}")
            return saveUser(user)

        }
    }

    def image(User user) {

        String profileImagePath = user.photo
        log.info(".........."+user.photo)

        if(user)

        { File imageFile =new File(profileImagePath);

            BufferedImage originalImage=ImageIO.read(imageFile);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();

            ImageIO.write(originalImage, "jpeg", baos );

            byte[] imageInByte=baos.toByteArray();

            return imageInByte
        }
        else
        {
            return null
        }

    }

}
