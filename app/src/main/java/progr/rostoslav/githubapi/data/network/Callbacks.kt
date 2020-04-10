package progr.rostoslav.githubapi.data.network

import okhttp3.ResponseBody
import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.data.DataRepository
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

open class Callbacks(dr: DataRepository) {
    private val parser = JSONParser()

    val commits: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) dr.model.reduce(
                Action.CommitsLoadedAction(parser.commitList(responseText))
            )
        }
    }

    val userReps: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) dr.model.reduce(
                Action.RepsLoadedAction(parser.repUserList(responseText))
            )
        }
    }
    val globalReps: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) dr.model.reduce(
                Action.RepsLoadedAction(parser.repGlobalList(responseText))
            )
        }
    }
    val repInfo: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) dr.model.reduce(
                Action.RepInfoLoadedAction(parser.repInfo(responseText))
            )
        }
    }
    val repItem: Callback<ResponseBody> = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) = t.printStackTrace()

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val responseText = response.body()?.string()
            if (responseText != null) dr.model.reduce(
                Action.RepItemLoadAction(parser.repItem(responseText))
            )
        }
    }
}
