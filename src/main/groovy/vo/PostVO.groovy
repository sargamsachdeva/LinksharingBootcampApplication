package vo

class PostVO {

    Long resourceID
    Long topicID
    Long userID
    String description
    String url
    String filePath
    String topicName
    String userName
    String userFirstName
    String userLastName
    byte[] userPhoto
    boolean isRead
    Integer resourceRating
    Date postDate


    @Override
    public String toString() {
        return "PostVO{" +
                "resourceID=" + resourceID +
                ", topicID=" + topicID +
                ", userID=" + userID +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                ", topicName='" + topicName + '\'' +
                ", userName='" + userName + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +

                ", isRead=" + isRead +
                ", resourceRating=" + resourceRating +
                ", postDate=" + postDate +
                '}';
    }
}
