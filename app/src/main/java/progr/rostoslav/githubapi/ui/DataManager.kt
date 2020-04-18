package progr.rostoslav.githubapi.ui

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepInfo

class DataManager {
    companion object {

        private val followersViews = ArrayList<FollowerView>()
        fun addFolower(follower: FollowerView) {
            if (!followersViews.contains(follower)) followersViews.add(follower)
        }

        fun remoweFolower(follower: FollowerView) {
            if (followersViews.contains(follower)) followersViews.remove(follower)
        }

        private fun updateFollowersViews() {
            for (i in followersViews) i.updateView()
        }

        private var username = ""
        fun getUsername() = username + ""
        fun setUsername(_username: String) {
            username = _username
            updateFollowersViews()
        }

        private var rep_list = ArrayList<Rep>()
        fun getReps() = rep_list
        fun getSavedReps() = rep_list.filter { it.isSaved } as ArrayList<Rep>
        fun updateRep(old: Rep, new: Rep) {
            val position = rep_list.lastIndexOf(old)
            if (position != (-1)) rep_list[position] = new
        }

        fun udateReps(new_reps: ArrayList<Rep>) {
            rep_list = new_reps
            updateFollowersViews()
        }

        private var rep_info = RepInfo()
        fun getRepInfo() = rep_info
        fun setRepInfo(new: RepInfo) {
            rep_info = new.copy(commits = rep_info.copy().commits)
            updateFollowersViews()
        }

        fun updateCommits(c: List<Commit>) {
            rep_info.commits = c
            updateFollowersViews()
        }
    }
}
