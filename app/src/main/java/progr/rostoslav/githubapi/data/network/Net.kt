package progr.rostoslav.githubapi.data.network

import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import progr.rostoslav.githubapi.DataManager
import progr.rostoslav.githubapi.data.DataRepository
import progr.rostoslav.githubapi.domain.entities.RepInfo
import progr.rostoslav.githubapi.domain.entities.RepRealm
import progr.rostoslav.githubapi.domain.entities.toRepList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Net() {

    private fun getApi(): GitApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()

        val api: GitApi = retrofit.create(GitApi::class.java)
        return api
    }

    private val callbackList: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) {
                val reps_from_network = parseRepsJSON(responseText)
                DataManager.udateReps(reps_from_network.toRepList())
//                DataRepository().saveRepsToDB(reps_from_network)
//                DataManager.udateReps(DataRepository().loadRepsFromDB())
            }
        }
    }

    private val callbackRepos: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) {
                val rep = parseRepInfoJSON(responseText)
                DataManager.udateRepInfo(rep)
            }
        }
    }
    fun getRepFromServer(author: String, rep_name: String) =
        getApi().getRep(author, rep_name).enqueue(callbackRepos)

    fun getRepsFromServer(author: String) =
        getApi().getReps(author).enqueue(callbackList)

//    fun getRepFromServer(author: String, rep_name: String) =
//        getApi().getRep(author, rep_name).enqueue(callbackRepos)

    fun parseRepsJSON(responseText: String): ArrayList<RepRealm> {
        val r = ArrayList<RepRealm>()
        val jsonArray = JSONArray(responseText)
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            val item = RepRealm()
            item.title = jsonObject.getString("name")
            item.author = jsonObject.getString("full_name").split("/")[0]
            item.description = jsonObject.getString("description")
            item.lang = jsonObject.getString("language")
            item.forks_count = jsonObject.getInt("forks")
            item.stars_count = jsonObject.getInt("stargazers_count")
            item.commits_count = jsonObject.getInt("open_issues_count") + 7//TODO FIX WRONG DATA

            r.add(item)
        }
        return r
    }

    fun parseRepInfoJSON(responseText: String): RepInfo {
        val jsonObject = JSONObject(responseText)
        val jO_owner = JSONObject(jsonObject.getString("owner"))

        val r = RepInfo(
            title = jsonObject.getString("name"),
            description = jsonObject.getString("description"),
            lang = jsonObject.getString("language"),
            forks_count = jsonObject.getInt("forks"),
            stars_count = jsonObject.getInt("stargazers_count"),
            commits_count = jsonObject.getInt("open_issues_count") + 7,//TODO FIX WRONG DATA
            full_name = jsonObject.getString("full_name"),
            login = jO_owner.getString("login"),
            node_id = jO_owner.getString("node_id"),
            avatar_url = jO_owner.getString("avatar_url"),
            url = jO_owner.getString("url"),
            created_at = jsonObject.getString("created_at"),
            updated_at = jsonObject.getString("updated_at"),
            size = jsonObject.getInt("size"),
            network_count = jsonObject.getInt("network_count"),
            subscribers_count = jsonObject.getInt("subscribers_count"),
            open_issues = jsonObject.getInt("open_issues"),
            watchers = jsonObject.getInt("watchers"),
            default_branch = jsonObject.getString("default_branch"),
            has_issues = jsonObject.getBoolean("has_issues"),
            has_projects = jsonObject.getBoolean("has_projects"),
            has_downloads = jsonObject.getBoolean("has_downloads"),
            has_wiki = jsonObject.getBoolean("has_wiki")
        )

        return r
    }
}