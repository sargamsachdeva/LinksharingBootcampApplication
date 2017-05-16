package com.ttn.linksharingbootcamp

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ResourceRating)

class ResourceRatingSpec extends Specification {

    def "checkResourceRating"() {

        given:
        ResourceRating resourceRating = new ResourceRating(resource: resource, user: user, score: score)

        when:
        Boolean valid = resourceRating.validate()

        then:
        valid == result

        where:
        resource               | user       | score | result
        new DocumentResource() | new User() | 3     | true
        null                   | new User() | 3     | false
        new DocumentResource() | null       | 3     | false
        new DocumentResource() | new User() | 10    | false
        new DocumentResource() | new User() | 0     | false

    }

    def "validateUniqueResourceRating"() {

        given:
        Resource resource = new LinkResource()
        User user = new User()
        ResourceRating resourceRating = new ResourceRating(resource: resource, user: user, score: 3)
        ResourceRating newResourceRating = new ResourceRating(resource: resource, user: user, score: 4)

        when:
        resourceRating.save(flush: true)
        newResourceRating.save(flush: true)

        then:
        !resourceRating.errors.allErrors.size()
        newResourceRating.errors.allErrors.size()
        newResourceRating.errors.getFieldError('resource')
    }

    def "tostringCheck"() {
        setup:

        User user = new User(userName: userName)
        Resource resource = new DocumentResource(description: description)
        ResourceRating resourceRating = new ResourceRating(user: user, resource: resource, score: score)

        when:
        result == resourceRating.toString()

        then:
        noExceptionThrown()

        where:
        userName          | description | score | result
        "sargamsachdeva"  | "grails"     | 5      | "sargamsachdeva rated grails by 5"
    }
}
