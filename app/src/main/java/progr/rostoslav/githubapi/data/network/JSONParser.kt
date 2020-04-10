package progr.rostoslav.githubapi.data.network

import org.json.JSONArray
import org.json.JSONObject
import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepInfo

class JSONParser() {
    val ERROR = "\"documentation_url\": \"https://developer.github.com/"
    fun repUserList(responseText: String): ArrayList<Rep> {
        val r = ArrayList<Rep>()
        val jsonArray = JSONArray(responseText)
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            r.add(
                Rep(
                    title = jsonObject.getString("name"),
                    author = jsonObject.getString("full_name").split("/")[0],
                    description = jsonObject.getString("description"),
                    lang = jsonObject.getString("language"),
                    forks_count = jsonObject.getInt("forks"),
                    stars_count = jsonObject.getInt("stargazers_count"),
                    commits_count = jsonObject.getInt("open_issues_count") + 7//TODO FIX WRONG DATA
                )
            )
        }
        return r
    }

    fun repGlobalList(responseText: String): ArrayList<Rep> {
        val r = ArrayList<Rep>()
        val jsonArray = JSONArray(responseText)
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            val owner = JSONObject(jsonObject.getString("owner"))
            r.add(
                Rep(
                    title = jsonObject.getString("name"),
                    author = owner.getString("login"),
                    autor_img = owner.getString("avatar_url"),
                    description = jsonObject.getString("description"),
                    lang = "",
                    forks_count = 0,
                    stars_count = 0,
                    commits_count = 0
                )
            )
        }
        return r
    }

    fun commitList(responseText: String): ArrayList<Commit> {
        val r = ArrayList<Commit>()
        val jsonArray = JSONArray(responseText)
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            println(jsonObject.toString())
            val commit = JSONObject(jsonObject.getString("commit"))
            val c_commiter = JSONObject(commit.getString("committer"))
            val avatar_url =
                if (jsonObject.getString("committer") == "null") "https://avatars3.githubusercontent.com/u/19864447?v=4"
             else JSONObject(jsonObject.getString("committer")).getString("avatar_url")

            r.add(
                Commit(
                    author = c_commiter.getString("name"),
                    author_img = avatar_url,
                    date = c_commiter.getString("date"),
                    title = commit.getString("message")
                )
            )
        }
        return r
    }

    fun repInfo(responseText: String): RepInfo {
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
            avatar_url = jO_owner.getString("avatar_url"),
            created_at = jsonObject.getString("created_at"),
            updated_at = jsonObject.getString("updated_at"),
            size = jsonObject.getInt("size")
        )
        return r
    }
    fun repItem(responseText: String): Rep {
        val jsonObject = JSONObject(responseText)
        val jO_owner = JSONObject(jsonObject.getString("owner"))
        val r = Rep(
            title = jsonObject.getString("name"),
            description = jsonObject.getString("description"),
            lang = jsonObject.getString("language"),
            forks_count = jsonObject.getInt("forks"),
            stars_count = jsonObject.getInt("stargazers_count"),
            commits_count = jsonObject.getInt("open_issues_count") + 7,//TODO FIX WRONG DATA
            autor_img = jO_owner.getString("avatar_url")
        )
        return r
    }
}