package progr.rostoslav.githubapi.domain.entities

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
}
fun List<RepRealm>.toRepList():ArrayList<Rep>{
    var r= ArrayList<Rep>()
    for(i in this){r.add(i.toRep())}
    return r
}

fun RepRealm.toRep(): Rep {
    var r = Rep(
        title = this.title,
        description = this.description,
        lang = this.lang,
        forks_count =  this.forks_count,
        stars_count = this.stars_count,
        commits_count = this.commits_count,
        isSaved =  this.isSaved
    )
    return r
}

fun ArrayList<Rep>.toRealmList():ArrayList<RepRealm>{
    var r= ArrayList<RepRealm>()
    for(i in this){r.add(i.toRepRealm())}
    return r
}
