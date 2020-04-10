package progr.rostoslav.githubapi.entities

import io.realm.RealmObject


open class RepRealm : RealmObject(){
    var title: String=""
    var description: String=""
    var lang: String=""
    var forks_count: Int=0
    var stars_count: Int=0
    var commits_count: Int=0
    var isSaved: Boolean = false
    var author: String = ""
    var user_key: String =""
}
