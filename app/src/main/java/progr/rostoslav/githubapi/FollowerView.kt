package progr.rostoslav.githubapi

interface FollowerView {

    fun updateView()
    fun toFollowView(follower: FollowerView) {
        DataManager.addFolower(follower)
    }

    fun toUnfollowView(follower: FollowerView) {
        DataManager.remoweFolower(follower)
    }
}