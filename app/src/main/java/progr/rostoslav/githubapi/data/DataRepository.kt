package progr.rostoslav.githubapi.data

import progr.rostoslav.githubapi.ui.DataManager
import progr.rostoslav.githubapi.data.local.FakeData
import progr.rostoslav.githubapi.data.local.Local
import progr.rostoslav.githubapi.data.network.Net
import progr.rostoslav.githubapi.entities.Rep

class DataRepository {
    val fakeData = FakeData()
    val net = Net()
    val local = Local()

    fun init() {
        val saved = local.getReps()
        val from_net = DataManager.getReps()
        val r = ArrayList<Rep>()
        for (i in saved) {
                var rep = from_net.findLast {it.title+""+it.author==i.title+""+i.author }
                if(rep!=null) {
                    r.add(rep.copy(isSaved = i.isSaved))
                from_net.remove(rep)
                }
             else r.add(i)
        }
        r.addAll(from_net)
        DataManager.udateReps(r)
    }


    fun getCommits(count: Int = 10) {
        var r = fakeData.getCommits(count)
        DataManager.updateCommits(r)
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

    fun saveReps(reps:ArrayList<Rep>){
        local.saveReps(reps)
    }

}
/*fun saveRepsToDB(new_reps: ArrayList<RealmRep>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.copyToRealm(new_reps)
    realm.commitTransaction()
}

fun saveStateRepsToDB(reps: ArrayList<RepItem>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.copyToRealm(reps.toRealmList())
    realm.commitTransaction()
}

fun loadRealmRepFromDB():List<RealmRep> {
    val realm = Realm.getDefaultInstance()
    val old_list = DataManager.getReps()
    return  realm.where(RealmRep::class.java).findAll()
}
fun loadRepsFromDB():ArrayList<RepItem>{
    return loadRealmRepFromDB().toRepItemList()
}

fun mergeListsFromDBandNW(network_l:List<RealmRep>, database_l:List<RealmRep>):ArrayList<RealmRep>{
    var r =ArrayList<RealmRep>()
    var n_l= network_l as ArrayList<RealmRep>
    var db_l = database_l as ArrayList<RealmRep>
    r.addAll(n_l)
    for(i in r){
        val rep = db_l.find { it.author==i.author&&it.title==i.title }
        if(rep!=null){i.isFauv=rep.isFauv }
    }
    r.addAll(network_l)
    val end_range= listOf(network_l.lastIndex, db_l.lastIndex).max()?:0
    for(i in 0..end_range){
        val rep= RealmRep()
        with(rep){
            title
            author
            description
            commits_count
            forks_count
            lang
            stars_count

            isFauv
        }

    }

    return r
}*/
