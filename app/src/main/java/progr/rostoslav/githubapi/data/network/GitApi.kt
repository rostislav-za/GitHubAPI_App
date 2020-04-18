package progr.rostoslav.githubapi.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {

    @GET("/repos/{username}/{repo_name}")
    fun getRep(
        @Path("username") username: String = "rostislav-za",
        @Path("repo_name") repos_name: String = "GitHubAPI"
    ): Call<ResponseBody>

    @GET("/repos/{username}/{repo_name}/commits")
    fun getCommits(
        @Path("username") username: String = "rostislav-za",
        @Path("repo_name") repos_name: String = "GitHubAPI"
    ): Call<ResponseBody>

    @GET("/users/{username}/repos")
    fun getUserReps(
        @Path("username") username: String = "octokit"
    ): Call<ResponseBody>

    @GET("/repositories")
    fun getGlobalReps(
    ): Call<ResponseBody>
}