package progr.rostoslav.githubapi.domain.entities

import progr.rostoslav.githubapi.R

data class Rep(
    val title: String,
    val description: String,
    val lang: String,
    val forks_count: Int,
    val stars_count: Int,
    val commits_count: Int,
    var isSaved: Boolean = false,
    var author: String = ""
) {
    val isSavedRes = if (isSaved) R.drawable.saved
    else R.drawable.unsaved

    fun toRepInfo(): RepInfo {
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
    fun toRepRealm(): RepRealm {
        var r = RepRealm()
        r.title=this.title
        r.description=this.description
        r.lang=this.lang
        r.forks_count=this.forks_count
        r.stars_count=this.stars_count
        r.commits_count=this.commits_count
        r.author=this.author
        r.isSaved=this.isSaved
        return r
    }

}
