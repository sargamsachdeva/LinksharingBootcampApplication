package com.ttn.linksharingbootcamp

import enums.Visibility
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ReadingItem)
class ReadingItemSpec extends Specification {

    def "constraintsOfResourceItemExcludingUserUniqueness"() {

        given:
        ReadingItem resourceItemObj = new ReadingItem(resource: resource, user: user, isRead: isRead)

        when:
        boolean result = resourceItemObj.validate()

        then:
        result == excepted

        where:
        sno | resource           | user       | isRead | excepted
        1   | new LinkResource() | new User() | true   | true
        2   | null               | new User() | true   | false
        3   | new LinkResource() | null       | true   | false
        4   | new LinkResource() | new User() | null   | false
    }

    def "validatingUniqueReadingItem"() {

        given:
         //Resource resource = new LinkResource(url:"https://github.com/",description: "cdsjdjb",new User()).save()
        //User user = new User().save()

        User user1 = new User(email:"sargam.sachdeva@tothenew.com", userName: "sargam", password: "0987653", firstName: "Sargam",
                lastName: "Sachdeva")

        Topic topic = new Topic(name: "Grails", visibility: Visibility.PRIVATE, createdBy: user1)

        Resource resource = new LinkResource(url: "https://github.com/", description: "sdsdd", createdBy: user1, topic: topic)

        User user=new User(email:"sargam4@gmail.com", userName: "s", password: "1234532", firstName: "Sarf", lastName: "Kumr", photo: null,
        admin: true, active: false)

        ReadingItem readingItem = new ReadingItem(resource: resource, user: user, isRead: true)
        ReadingItem newReadingItem = new ReadingItem(resource: resource, user: user, isRead: false)

        when:
        readingItem.save(flush: true)
        newReadingItem.save(flush: true)

        then:

        ReadingItem.count() == 1
        newReadingItem.errors.allErrors.size() == 1
        newReadingItem.errors.getFieldErrorCount('resource') == 1

    }


    def "tostringCheck"() {

        setup:
        User user = new User(userName: userName).save()
        Resource resource = new DocumentResource(description: description).save()
        ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: isRead)

        when:
        result == readingItem.toString()

        then:
        noExceptionThrown()

        where:
        userName | description | isRead | result
        "sargam" | "grails"    | true   | "sargam read the grails: true"
    }
}