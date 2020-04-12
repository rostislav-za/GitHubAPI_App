package progr.rostoslav.githubapi.domain

import android.content.Intent
import android.content.SharedPreferences
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.Reducer
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.data.local.APP_USER
import progr.rostoslav.githubapi.data.local.USER_LOGIN
import progr.rostoslav.githubapi.data.local.USER_PASSWORD
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.User
import progr.rostoslav.githubapi.entities.toRepInfo
import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.ui.activityes.ActionProvider
import progr.rostoslav.githubapi.ui.activityes.BaseActivity
import progr.rostoslav.githubapi.ui.activityes.LoginActivity
import progr.rostoslav.githubapi.ui.activityes.MainActivity

class AppModel() : Reducer {
    lateinit var dr: DataRepository
    var user = User("", "")
    lateinit var activity: AppCompatActivity

    fun onCreate(email: String, pass: String, _activity: AppCompatActivity) {
        activity = _activity
        user = User(email, pass)
        DataManager.updateUsername(user.email)
        dr = DataRepository(this)
        dr.init(user.key)

        DataManager.udateReps(dr.getLoadedReps())
//        dr.getReps()
        dr.getUserReps("octokit")
        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits("rostislav-za", "GitHubAPI")
    }

    fun onDestroy() = saveData()

    override fun reduce(a: Action) {
        when (a) {
            is Action.UIRepClickedAction -> {
                dr.getRepInfo(a.rep)
                dr.getCommits(a.rep.author, a.rep.title)
                DataManager.udateRepInfo(a.rep.toRepInfo())
                DataManager.updateCommits(emptyList())
            }
            is Action.UIRepSavedChangedAction -> {
                val copy = a.rep.copy(isSaved = !a.rep.isSaved)
                DataManager.updateRep(a.rep, copy)
            }
            is Action.UIRefreshedListAction -> {
                dr.getReps()
            }
            is Action.RepInfoLoadedAction -> DataManager.udateRepInfo(a.rep_info)
            is Action.RepsLoadedAction -> {
                DataManager.udateReps(mergeListFromNet(DataManager.getReps(), a.new_reps))
            }
            is Action.CommitsLoadedAction -> {
                DataManager.updateCommits(a.new_commits)
            }
            is Action.RepItemLoadAction -> {
                val list = DataManager.getReps()
                val r = list.find { it.title + it.author == a.rep.title + a.rep.author }
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
