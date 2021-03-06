package progr.rostoslav.githubapi.entities

fun Rep.toRepRealm(): RepRealm {
    val r = RepRealm()
    r.title = this.title
    r.description = this.description
    r.lang = this.lang
    r.forks_count = this.forks_count
    r.stars_count = this.stars_count
    r.commits_count = this.commits_count
    r.author = this.author
    r.isSaved = this.isSaved
    r.user_key = this.user_key
    r.author_img = this.autor_img
    return r
}

fun RepRealm.toRep(): Rep {
    val r = Rep(
        title = this.title,
        description = this.description,
        lang = this.lang,
        forks_count = this.forks_count,
        stars_count = this.stars_count,
        commits_count = this.commits_count,
        isSaved = this.isSaved,
        user_key = this.user_key,
        author = this.author,
        autor_img = this.author_img
    )
    return r
}

fun List<RepRealm>.toRepList(): ArrayList<Rep> {
    val r = ArrayList<Rep>()
    for (i in this) r.add(i.toRep())
    return r
}

fun List<Rep>.toRealmList(): List<RepRealm> {
    val r = ArrayList<RepRealm>()
    for (i in this) r.add(i.toRepRealm())
    return r
}