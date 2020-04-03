package progr.rostoslav.githubapi.entities

fun Rep.toRepRealm(): RepRealm {
    var r = RepRealm()
    r.title = this.title
    r.description = this.description
    r.lang = this.lang
    r.forks_count = this.forks_count
    r.stars_count = this.stars_count
    r.commits_count = this.commits_count
    r.author = this.author
    r.isSaved = this.isSaved
    return r
}

fun RepRealm.toRep(): Rep {
    var r = Rep(
        title = this.title,
        description = this.description,
        lang = this.lang,
        forks_count = this.forks_count,
        stars_count = this.stars_count,
        commits_count = this.commits_count,
        isSaved = this.isSaved
    )
    return r
}

fun List<RepRealm>.toRepList(): ArrayList<Rep> {
    var r = ArrayList<Rep>()
    for (i in this) {
        r.add(i.toRep())
    }
    return r
}

fun ArrayList<Rep>.toRealmList(): ArrayList<RepRealm> {
    var r = ArrayList<RepRealm>()
    for (i in this) {
        r.add(i.toRepRealm())
    }
    return r
}


fun Rep.toRepInfo(): RepInfo {
    return RepInfo(
        title = this.title,
        lang = this.lang,
        stars_count = this.stars_count,
        commits_count = this.commits_count,
        forks_count = this.forks_count,
        isSaved = this.isSaved,
        description = this.description,
        full_name = this.author
    )
}
