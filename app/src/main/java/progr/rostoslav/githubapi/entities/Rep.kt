package progr.rostoslav.githubapi.entities

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
    /*, var autor_img: String = "",
    var list: List<Commit>? = null,
    var user_key: String*/
) {
    val isSavedRes = if (isSaved) R.drawable.saved
    else R.drawable.unsaved
}
