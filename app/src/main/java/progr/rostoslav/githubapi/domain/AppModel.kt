package progr.rostoslav.githubapi.domain

import android.content.Intent
import android.content.SharedPreferences
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import progr.rostoslav.githubapi.Action
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

class AppModel() {
    val dr = DataRepository(this)
    var user = User("", "")
    lateinit var activity: AppCompatActivity

    fun onCreate(email: String, pass: String,_activity: AppCompatActivity) {
        println("MODEL CREATE $email $pass  ")
        activity = _activity
        user=User(email,pass)
       DataManager.updateUsername(email)
        dr.init()

        DataManager.udateReps(dr.getLoadedReps())

        dr.getReps("octokit")
        dr.getRepInfo("rostislav-za", "GitHubAPI")
        dr.getCommits(10)
    }

    fun onDestroy() {
        saveData()
    }

    fun reduce(a: Action) {
        when (a) {
            is Action.UIRepClickedAction -> {
                dr.getRepInfo(a.rep)
                DataManager.udateRepInfo(a.rep.toRepInfo())
            }
            is Action.UIRepSavedChangedAction -> {
                val copy = a.rep.copy(isSaved = !a.rep.isSaved)
                DataManager.updateRep(a.rep, copy)
            }
            is Action.UIRefreshedListAction -> dr.getReps("octokit")
            is Action.RepInfoLoadedAction -> DataManager.udateRepInfo(a.rep_info)
            is Action.RepsLoadedAction -> DataManager.udateReps(
                mergeListFromNet(
                    DataManager.getReps(),
                    a.new_reps
                )
            )
        }
    }

    fun logOut(it: MenuItem): Boolean {
        onDestroy()
        (activity as ActionProvider).startLoginActivity()
        return true
    }

    fun mergeListFromNet(old_list: ArrayList<Rep>, new_list: ArrayList<Rep>): ArrayList<Rep> {
        val r = ArrayList<Rep>()
        for (i in old_list.filter { it.isSaved }) {
            val rep =
                (new_list.findLast { (it.title == i.title) && (it.author == i.author) })?.copy()
            if (rep != null) new_list[new_list.lastIndexOf(rep)] = rep.copy(isSaved = i.isSaved)
            else r.add(i)
        }
        r.addAll(new_list)
        return r
    }

    fun saveData() {

        dr.saveReps(DataManager.getSavedReps())
    }

}
