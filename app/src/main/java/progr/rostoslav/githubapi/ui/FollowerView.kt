package progr.rostoslav.githubapi.ui

interface FollowerView {

    fun updateView()
    fun toFollowView(follower: FollowerView) =DataManager.addFolower(follower)

    fun toUnfollowView(follower: FollowerView) =DataManager.remoweFolower(follower)

}