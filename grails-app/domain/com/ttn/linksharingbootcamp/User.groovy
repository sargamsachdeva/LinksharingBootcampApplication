package com.ttn.linksharingbootcamp

import co.SearchCO
import co.UserCO
import co.UserSearchCO
import enums.Visibility

class User {

    String email;
    String userName;
    String password;
    String firstName;
    String lastName;

    String photo;
    Boolean admin;
    Boolean active;
    String confirmPassword
    Date dateCreated;
    Date lastUpdated;


    static transients = ['fullName'
                        ,'confirmPassword',
                         'subscribedTopics']

    static hasMany = [topics : Topic,
                      subscriptions: Subscription,
                      resources:Resource,
                      readingItems:ReadingItem,
                      resourceRatings:ResourceRating]


    static mapping = {

        id(sort: 'desc')
    }


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
        admin(nullable: true)
        active(nullable: true)

        confirmPassword(nullable: true, blank: true)
    }

    String getFullName() {
        [firstName, lastName].join(' ')

    }

    String toString() {

        return this.userName +" " +this.firstName
    }

    static namedQueries = {

        search { UserSearchCO userSearchCO ->
            if (userSearchCO.isActive != null) {
                eq('active', userSearchCO.isActive)
            }
            if (userSearchCO.q) {
                or {
                    ilike('firstName', "%${userSearchCO.q}%")
                    ilike('lastName', "%${userSearchCO.q}%")
                    ilike('userName', "%${userSearchCO.q}%")
                    ilike('email', "%${userSearchCO.q}%")
                }
            }
            eq('admin', false)
        }
    }


    List<Topic> getSubscribedTopics() {

        List<Topic> topicList = Subscription.createCriteria().list {
            projections {
                property('topic')
            }
            eq('user.id', id)
        }
        return topicList
    }

    User userDetails() {
        return this
    }

    static Boolean isUserAdmin(User user) {
        return user && user.admin
    }

    static Boolean isUserActive(User user) {
        return user && user.active
    }


    static Boolean isResourceCreatedBy(Resource resource, User user) {
        return user && resource && (resource.createdBy.id == user.id)
    }

    static Boolean ifUserCanEditDeleteResource(Resource resource, User user) {
        return user && resource && (isUserAdmin(user) || isResourceCreatedBy(resource, user))
    }

    Integer getScore(Resource resource) {
        ResourceRating resourceRating = ResourceRating.findByUserAndResource(this, resource)
        return resourceRating ? resourceRating.score : 1

    }

    static Boolean ifUserExists(UserCO user) {
        return (user && User.countByUserNameAndEmail(user.userName, user.password))
    }


    List <ReadingItem> getUnReadResources(SearchCO searchCO)
    {
        List<ReadingItem> readingItems
        if(searchCO.q)
        {
            readingItems =ReadingItem.createCriteria().list(max:searchCO.max,offset:searchCO.offset) {

                eq('user', this)
                eq('isRead', false)
                ilike('resource.description',searchCO.q)
            }

        }
        return readingItems
    }


    static Boolean isCurrentUser(User user1, User user2) {
        return user1 && user2 && (user1.id == user2.id)
    }

    Integer getPostsCount() {
        return Resource.countByCreatedBy(this) ?: 0
    }

    static Boolean ifTopicIsCreatedBy(Topic topic, User user) {
        return user && topic && (topic.createdBy.id == user.id)
    }



    /*
     List<Resource> unreadResources() {

         return ReadingItem.createCriteria().list {
             projections {
                 property('resource')
             }
             eq('user', this)
             eq('isRead', false)
         }
     }
 */
    Integer getTotalReadingItem() {
        return ReadingItem.countByUser(this) ?: 0
    }

    def readingItems(SearchCO searchCO) {
        return ReadingItem?.findAllByUser(this, [max: searchCO.max, offset: searchCO.offset])
    }

    def userSubscriptions() {
        return Subscription.createCriteria().list() {
            projections {
                'topic' {
                    property('id')
                    property('topicName')
                    property('visibility')
                }
                'user' {
                    eq('id', this.id)
                }
            }
        }
    }

    Subscription ifSubscriptionExist(Topic topic) {
        return Subscription.findByTopicAndUser(topic,this)

    }

    Subscription getSubscription(Long topicId) {
        return Subscription.findByUserAndTopic(this, Topic.load(topicId))
    }

    Map getPrivatelySubscribedTopics() {

        Map topicNameList = Subscription.createCriteria().list {
            projections {
                'topic' {
                    property('id')
                    property('topicName')
                    'user' {
                        property('userName')
                    }
                }
            }
            topic{
                eq('visibility', Visibility.PRIVATE)
            }
            eq('user.id', id)
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
        log.info("${topicNameList}")
        return topicNameList
    }
}


