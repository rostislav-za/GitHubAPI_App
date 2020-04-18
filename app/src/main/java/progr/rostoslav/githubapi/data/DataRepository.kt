package progr.rostoslav.githubapi.data

import progr.rostoslav.githubapi.data.local.*
import progr.rostoslav.githubapi.data.network.Net
import progr.rostoslav.githubapi.domain.AppModel
import progr.rostoslav.githubapi.entities.Rep

class DataRepository(val model: AppModel) {
    private val fakeData = FakeData()
    private val net = Net(this, model.user)
    private val local = Local()
    lateinit var user_key: String
    fun init(_user_key: String) {
        user_key = _user_key
    }

    fun getLoadedReps() = local.getReps(user_key)

    fun getCommits(name: String, title: String) = net.getCommitsFromServer(name, title)

    fun getReps() = net.getGlobalRepsFromServer()

    fun getRepItems(reps: List<Rep>) = net.getGlobalRepsItemsFromServer(reps)

    fun getUserReps(author: String) = net.getRepsFromServer(author)

    fun getRepInfo(rep: Rep) = net.getRepFromServer(rep.author, rep.title)

    fun getRepInfo(author: String, title: String) = net.getRepFromServer(author, title)

    fun saveReps(list: List<Rep>) = local.saveReps(list, user_key)
}