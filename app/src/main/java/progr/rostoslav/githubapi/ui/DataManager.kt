package progr.rostoslav.githubapi.ui

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepInfo

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

        fun getRepInfo() =
            rep_info
        fun getReps() =
            rep_list
        fun getSavedReps() = rep_list.filter { it.isSaved } as ArrayList<Rep>


        fun udateRepInfo(new: RepInfo) {
            rep_info = new
            updateFollowersViews()
        }

        fun udateReps(new_reps: ArrayList<Rep>) {
            /* val list_in_db = local.getReps()
        val r = ArrayList<Rep>()
        for (i in list_in_db) {
            val rep = list.findLast { (i.author == it.author) && (i.title == it.title) }
            if (rep != null) r.add(rep.copy(isSaved = i.isSaved)) else r.add(i)
        }
        for (i in list) if (!r.contains(i)) r.add(i)*/
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
