package progr.rostoslav.githubapi.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.User
import progr.rostoslav.githubapi.ui.DataManager
import retrofit2.Retrofit

class Net(dr: DataRepository, user: User) {
    val baseAuth = "${user.email}:${user.password}@"
    val baseUrl = "https://${baseAuth}api.github.com/"
    val cb = Callbacks(dr)
    var api: GitApi? = null

    fun initApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
        api = retrofit.create(GitApi::class.java)
    }

    fun getGlobalRepsFromServer() = api?.getGlobalReps()?.enqueue(cb.globalReps)

    fun getGlobalRepsItemsFromServer(reps: List<Rep>) = GlobalScope.launch(Dispatchers.Main) {
        for (i in reps) {
            delay(100)
            api?.getRep(i.author, i.title)?.enqueue(cb.repItem)
            delay(100)
            api?.getCommits(i.author, i.title)?.enqueue(cb.commits)
        }
        delay(2000)
        DataManager.updateFollowersViews()
    }

    fun getRepsFromServer(author: String = "octocat") =
        api?.getUserReps(author)?.enqueue(cb.userReps)

    fun getCommitsFromServer(author: String = "rostislav-za", rep_name: String = "GitHubAPI") =
        api?.getCommits(author, rep_name)?.enqueue(cb.commits)
}