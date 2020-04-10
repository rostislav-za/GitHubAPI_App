package progr.rostoslav.githubapi.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GitApi {
    @GET("/users/{username}/repos")
    fun getReps(
        @Path("username") username: String="octokit"
    ): Call<ResponseBody>

    @GET("/repos/{username}/{repo_name}")
    fun getRep(
        @Path("username") username: String="rostislav-za",
        @Path("repo_name") repos_name: String="GitHubAPI"
    ): Call<ResponseBody>

/*добавить запрос на комиты и добавить запрос на каждый репозиторий, добавить в запросы  базовую авторизацию*/
}