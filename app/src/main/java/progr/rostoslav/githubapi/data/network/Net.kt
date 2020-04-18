package progr.rostoslav.githubapi.data.network

import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.User
import retrofit2.Retrofit

class Net(dr: DataRepository, user: User) {
    val baseAuth = "${user.email}:${user.password}@"
    val baseUrl = "https://${baseAuth}api.github.com/"
    val cb = Callbacks(dr)

    private fun api(): GitApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
        val api: GitApi = retrofit.create(GitApi::class.java)
        return api
    }

    fun getGlobalRepsFromServer() = api().getGlobalReps().enqueue(cb.globalReps)

    fun getGlobalRepsItemsFromServer(reps: List<Rep>) {
        for (i in reps) api().getRep(i.author, i.title).enqueue(cb.repItem)
    }

    fun getRepFromServer(author: String, rep_name: String) =
        api().getRep(author, rep_name).enqueue(cb.repInfo)

    fun getRepsFromServer(author: String = "octocat") =
        api().getUserReps(author).enqueue(cb.userReps)

    fun getCommitsFromServer(author: String = "rostislav-za", rep_name: String = "GitHubAPI") =
        api().getCommits(author, rep_name).enqueue(cb.commits)
}