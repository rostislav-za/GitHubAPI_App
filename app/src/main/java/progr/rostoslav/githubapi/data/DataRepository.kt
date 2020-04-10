package progr.rostoslav.githubapi.data

import androidx.appcompat.app.AppCompatActivity
import progr.rostoslav.githubapi.data.local.*
import progr.rostoslav.githubapi.data.network.Net
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep

class DataRepository(val model: AppModel) {
  private val fakeData = FakeData()
  private val net = Net(this)
  private val local = Local()

    fun init() {

    }

    fun getLoadedReps():ArrayList<Rep>{
        return local.getReps()
    }


    fun getCommits(count: Int = 10) {
        val r = fakeData.getCommits(count)
//        DataManager.updateCommits(r)
    }

    fun getReps(author: String) {
        net.getRepsFromServer(author)
    }

    fun getRepInfo(rep: Rep) {
        net.getRepFromServer(rep.author, rep.title)
    }

    fun getRepInfo(author: String, title: String) {
        net.getRepFromServer(author, title)
    }

    fun saveReps(list: List<Rep>) {
        local.saveReps(list)
    }

}