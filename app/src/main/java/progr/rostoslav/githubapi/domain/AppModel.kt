package progr.rostoslav.githubapi.domain


import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.Reducer
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.User
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.ui.activityes.ActionProvider

class AppModel() : Reducer {
    lateinit var dr: DataRepository
    var user = User("", "")
    lateinit var activity: AppCompatActivity

    fun onCreate(email: String, pass: String, _activity: AppCompatActivity) {
        activity = _activity
        user = User(email, pass)
        DataManager.setUsername(user.email)
        dr = DataRepository(this)
        dr.init(user.key)

        DataManager.udateReps(dr.getLoadedReps())
        // dr.getUserReps("octokit")
        dr.getReps()

//        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits("rostislav-za", "GitHubAPI")
    }

    fun onDestroy() = saveData()

    override fun reduce(a: Action) {
        when (a) {
            is Action.UIRepClickedAction -> {
                // dr.getRepInfo(a.rep)
                dr.getCommits(a.rep.author, a.rep.title)
              val ind = DataManager.getReps().indexOf(a.rep)
                DataManager.setRepInfo(ind)
            }
            is Action.UIRepSavedChangedAction -> {
                val copy = a.rep.copy(isSaved = !a.rep.isSaved)
                DataManager.updateRep(a.rep, copy)
            }
            is Action.UIRefreshedListAction -> dr.getReps()

            //is Action.RepInfoLoadedAction -> DataManager.setRepInfo(a)
            is Action.RepsLoadedAction -> {
                DataManager.udateReps(mergeListFromNet(DataManager.getReps(), a.new_reps))
                dr.getRepItems(DataManager.getReps().subList(0, 5))
            }
            is Action.CommitsLoadedAction -> {
                val list = DataManager.getReps()
                val r = list.findLast { (it.author + "/" + it.title == a.new_commits[0].parent) }
                list[list.lastIndexOf(r)].commits_count = a.new_commits.size
                list[list.lastIndexOf(r)].commits = a.new_commits
                DataManager.udateReps(list)
              //  DataManager.updateCommits(a.new_commits)
            }
            is Action.RepItemLoadAction -> {
                a.rep.user_key = user.key + ""
                val list = DataManager.getReps()
                val r = list.findLast { (it.title == a.rep.title) && (it.author == a.rep.author) }
                list[list.lastIndexOf(r)] = a.rep

                DataManager.udateReps(list)
            }
        }
    }

    fun logOut(it: MenuItem) {
        onDestroy()
        (activity as ActionProvider).startLoginActivity()
    }

    fun mergeListFromNet(old_list: ArrayList<Rep>, new_list: ArrayList<Rep>): ArrayList<Rep> {
        for (i in new_list) i.user_key = user.key
        val r = ArrayList<Rep>()
        for (i in old_list.filter { it.isSaved }) {
            val rep =
                (new_list.findLast { (it.title + it.author + it.user_key == i.title + i.author + i.user_key) })?.copy()
            if (rep != null) new_list[new_list.lastIndexOf(rep)] =
                rep.copy(isSaved = i.isSaved, user_key = i.user_key)
            else r.add(i)
        }
        r.addAll(new_list)
        return r
    }

    fun saveData() = dr.saveReps(DataManager.getSavedReps())
}
