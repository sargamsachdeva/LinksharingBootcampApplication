package vo.conversion

import co.ResourceSearchCO
import co.SearchCO
import co.TopicSearchCO
import co.UserSearchCO
import com.ttn.linksharingbootcamp.Resource
import com.ttn.linksharingbootcamp.Topic
import com.ttn.linksharingbootcamp.User
import vo.PostVO
import vo.TopicVO
import vo.UserVO

class DomainToVO {
    static List<UserVO> subscribedUsers(Topic topic) {
        if (topic) {
            List<User> subscribedUsers = topic.subscribedUsers()
            List<UserVO> subscribedUsersVO = []
            subscribedUsers.each { User user ->
                subscribedUsersVO.add(new UserVO(
                        id: user.id,
                        name: user.userName,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        email: user.email)
                )
            }
            return subscribedUsersVO
        }
    }

    static TopicVO topicDetails(Topic topic) {
        if (topic) {
            Topic topicDetails = topic.topicDetails()
            TopicVO topicVO = new TopicVO()
            topicVO.id = topicDetails.id
            topicVO.topicName = topicDetails.topicName
            topicVO.createdBy = topicDetails.createdBy
            topicVO.visibility = topicDetails.visibility
            return topicVO
        }
    }


    static List<PostVO> topicPosts(Topic topic) {
        if (topic) {
            List<PostVO> topicPostsVO = []
            Resource.topicPosts(topic.id).each {
                topicPostsVO.add(new PostVO(
                        resourceID: it[0],
                        description: it[1],
                        url: it[2],
                        filePath: it[3],
                        topicID: it[4],
                        topicName: it[5],
                        userID: it[6],
                        userName: it[7],
                        userFirstName: it[8],
                        userLastName: it[9],
                        userPhoto: it[10],
                        isRead: "",
                        resourceRating: 0,
                        postDate: it[11])
                )
            }
            return topicPostsVO
        }
    }


    static List<PostVO> topPosts() {
        List<PostVO> topPostsVO = []
        Resource.topPosts().each {
            topPostsVO.add(new PostVO(
                    resourceID: it[0],
                    description: it[1],
                    url: it[2],
                    filePath: it[3],
                    topicID: it[4],
                    topicName: it[5],
                    userID: it[6],
                    userName: it[7],
                    userFirstName: it[8],
                    userLastName: it[9],
                    userPhoto: it[10],
                    isRead: "",
                    resourceRating: 0,
                    postDate: it[11]))
        }
        return topPostsVO
    }

    static List<PostVO> recentPosts() {
        List<PostVO> recentPostsVO = []
        Resource.recentPosts().each {
            recentPostsVO.add(new PostVO(
                    resourceID: it[0],
                    description: it[1],
                    url: it[2],
                    filePath: it[3],
                    topicID: it[4],
                    topicName: it[5],
                    userID: it[6],
                    userName: it[7],
                    userFirstName: it[8],
                    userLastName: it[9],
                    userPhoto: it[10],
                    isRead: "",
                    resourceRating: 0,
                    postDate: it[11])
            )
        }
        return recentPostsVO
    }

    static PostVO post(Resource resource) {
        def post = resource.post()
        return new PostVO(
                resourceID: post[0],
                description: post[1],
                url: post[2],
                filePath: post[3],
                topicID: post[4],
                topicName: post[5],
                isRead: post[6],
                userID: post[7],
                userName: post[8],
                userFirstName:post[9],
                userLastName: post[10],
                userPhoto: post[11],
                postDate: post[12],
                resourceRating: 0
        )
    }

    static List<TopicVO> trendingTopics() {
        List<TopicVO> trendingTopicsVO = []
        Topic.trendingTopics().each {
            trendingTopicsVO.add(new TopicVO(
                    id: it[0],
                    topicName:  it[1],
                    visibility: it[2],
                    count: it[3],
                    createdBy: it[4])
            )
        }
        return trendingTopicsVO
    }

    static List<PostVO> readingItems(User user, SearchCO searchCO) {
        List<PostVO> readingItemsVO = []
        user.readingItems(searchCO).each {
            readingItemsVO.add(new PostVO(
                    resourceID: it.resource.id,
                    description: it.resource.description,
                    topicID: it.resource.topic.id,
                    topicName: it.resource.topic.topicName,
                    userID: it.resource.createdBy.id,
                    userName: it.resource.createdBy.userName,
                    userFirstName: it.resource.createdBy.firstName,
                    userLastName: it.resource.createdBy.lastName,
                    userPhoto: it.resource.createdBy.photo,
                    isRead: it.isRead,
                    url: it.resource,
                    filePath: it.resource,
                    postDate: it.resource.lastUpdated)
            )
        }
        return readingItemsVO
    }

    static UserVO userDetails(User user) {
        if (user) {
            User userDetails = user.userDetails()
            UserVO userDetailsVO = new UserVO()
            userDetailsVO.id = userDetails.id
            userDetailsVO.name = userDetails.userName
            userDetailsVO.firstName = userDetails.firstName
            userDetailsVO.lastName = userDetails.lastName
            userDetailsVO.email = userDetails.email
            userDetailsVO.photo = userDetails.photo
            userDetailsVO.isActive = userDetails.active
            userDetailsVO.isAdmin = userDetails.admin
            return userDetailsVO
        }
    }

    static List<TopicVO> userSubscriptions(User user) {
        if (user) {
            List<TopicVO> userSubscriptionsVO = []
            user.userSubscriptions().each {
                userSubscriptionsVO.add(new TopicVO(
                        id: it[0],
                        topicName: it[1],
                        visibility: it[2],
                        count: 0,
                        createdBy: user)
                )
            }
            return userSubscriptionsVO
        }
    }


    static List<UserVO> registeredUsers(UserSearchCO userSearchCO, User user) {
        if (user && User.isUserAdmin(user) && User.isUserActive(user)) {
            List<UserVO> registeredUsersVO = []
            User.search(userSearchCO).list([sort  : userSearchCO.sort,
                                            order : userSearchCO.order,
                                            max   : userSearchCO.max,
                                            offset: userSearchCO.offset]).each {
                registeredUsersVO.add(new UserVO(
                        id: it.id,
                        name: it.userName,
                        firstName: it.firstName,
                        lastName: it.lastName,
                        email: it.email,
                        isActive: it.active)
                )
            }
            return registeredUsersVO
        }
    }


    static List<TopicVO> createdTopics(TopicSearchCO topicSearchCO) {
        List<TopicVO> createdTopics = []
        if (topicSearchCO.id) {
            List<Topic> topicList = Topic.createCriteria().list(max: topicSearchCO.max) {
                eq('createdBy.id', topicSearchCO.id)
                if (topicSearchCO.visibility) {
                    eq('visibility', topicSearchCO.visibility)
                }
            }
            topicList.each {
                topic ->
                    createdTopics.add(new TopicVO(id: topic.id, topicName: topic.topicName, visibility: topic
                            .visibility, createdBy: topic.createdBy))
            }
        }
        return createdTopics
    }

    static List<Topic> subscribedTopics(TopicSearchCO topicSearchCO) {
        if (topicSearchCO.id) {
            User user = topicSearchCO.getUser()
            return user.getSubscribedTopics()
        }
    }

    static List<PostVO> createdResources(ResourceSearchCO resourceSearchCO) {
        List<PostVO> resources = []
        Resource.search(resourceSearchCO).list([max   : resourceSearchCO.max,
                                                offset: resourceSearchCO.offset]).each {
            resources.add(DomainToVO.post(it))
        }
        return resources
    }
}
