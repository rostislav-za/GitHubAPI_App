package progr.rostoslav.githubapi.data.local

import io.realm.Realm
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepRealm
import progr.rostoslav.githubapi.entities.toRealmList
import progr.rostoslav.githubapi.entities.toRepList
import progr.rostoslav.githubapi.ui.DataManager

class Local() {

    fun saveReps(reps: ArrayList<Rep>) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.deleteAll()
        realm.copyToRealm(reps.toRealmList())
        realm.commitTransaction()
    }

    fun getReps(): ArrayList<Rep> {
        val realm = Realm.getDefaultInstance()
        val r = realm.where(RepRealm::class.java).findAll().toRepList()
        return r
    }


}