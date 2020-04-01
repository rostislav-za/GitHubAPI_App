package progr.rostoslav.githubapi

import progr.rostoslav.githubapi.domain.entities.Commit
import progr.rostoslav.githubapi.domain.entities.Rep
import progr.rostoslav.githubapi.domain.entities.RepInfo


class DataManager {
    companion object {
        private var rep_list = ArrayList<Rep>()
        private var rep_info = RepInfo()

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

        fun getRepInfo() = rep_info
        fun getReps() = rep_list
        fun getFauvReps() = rep_list.filter { it.isSaved } as ArrayList<Rep>


        fun udateRepInfo(new: RepInfo) {
            rep_info = new
        }

        fun udateReps(new_reps: ArrayList<Rep>) {
//            val realm= Realm.getDefaultInstance()
//            realm.beginTransaction()
//            realm.copyToRealm(new_reps.toRealmList())
//            realm.commitTransaction()

            rep_list = new_reps
            updateFollowersViews()
        }

        fun updateRep(old: Rep, new: Rep) {
            val position = rep_list.lastIndexOf(old)
            if (position != (-1)) rep_list[position] = new
        }

        fun updateCommits(c: List<Commit>) {
            rep_info.commits = c
        }


    }
}
