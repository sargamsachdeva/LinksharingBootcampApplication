package vo

class RatingInfoVO {

    Integer totalVotes
    Integer averageScore
    Integer totalScore

    @Override
    String toString() {
        return "${totalVotes}, ${averageScore}, ${totalScore}"
    }
}
