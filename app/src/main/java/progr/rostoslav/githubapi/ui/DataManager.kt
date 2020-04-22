package progr.rostoslav.githubapi.ui

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep

//import progr.rostoslav.githubapi.entities.RepInfo

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

        private var rep_info: Int = 0
        fun getRepInfo() = when {
            rep_list.isEmpty() -> Rep("Ошибка", "", "", 0, 0, 0, false, "")
            else -> if (rep_info in 0..rep_list.lastIndex) rep_list[rep_info] else {
                rep_info = 0
                rep_list[rep_info]
            }
        }
        fun setRepInfo(new: Int) {
            if (new in 0..rep_list.lastIndex) rep_info = new + 0
            updateFollowersViews()
        }

        fun updateCommits(c: ArrayList<Commit>) {
            getRepInfo().commits = c
            updateFollowersViews()
        }
    }
}
