package progr.rostoslav.githubapi.data.local

import io.realm.Realm
import io.realm.kotlin.where
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepRealm
import progr.rostoslav.githubapi.entities.toRealmList
import progr.rostoslav.githubapi.entities.toRepList

class Local() {

    fun saveReps(reps: List<Rep>, user_key: String) {

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        var r = realm.where(RepRealm::class.java).findAll().toRepList()
        val user_list = r.filter { it.user_key == user_key }
        r.removeAll(user_list)
        if (!reps.isEmpty()) r.addAll(reps)

        realm.deleteAll()
        realm.copyToRealm(r.toRealmList())
        realm.commitTransaction()
    }

    fun getReps(userKey: String): ArrayList<Rep> {
        val realm = Realm.getDefaultInstance()
        val r = realm.where(RepRealm::class.java).contains("user_key", userKey).findAll()
        return r.toRepList()
    }

}