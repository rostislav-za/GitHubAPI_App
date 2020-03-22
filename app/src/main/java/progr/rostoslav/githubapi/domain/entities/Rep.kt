package progr.rostoslav.githubapi.domain.entities

import progr.rostoslav.githubapi.R

data class Rep(
    val title: String,
    val description: String,
    val lang: String,
    val forks_count: Int,
    val stars_count: Int,
    val commits_count: Int,
    var isFauv: Boolean = false,
    var author: String = ""
) {
    val isFavRec = if (isFauv) R.drawable.saved
    else R.drawable.unsaved
    fun toRepInfo(): RepInfo {
        return RepInfo(
            title = this.title,
            lang = this.lang,
            stars_count = this.stars_count,
            commits_count = this.commits_count,
            forks_count = this.forks_count,
            isFav = this.isFauv,
            description = this.description,
            full_name = this.author
        )
    }

}
