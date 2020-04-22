package progr.rostoslav.githubapi.entities

import progr.rostoslav.githubapi.R

data class Rep(
    val title: String,
    val description: String,
    val lang: String,
    val forks_count: Int,
    val stars_count: Int,
    var commits_count: Int,
    var isSaved: Boolean = false,
    var author: String = "",
    var autor_img: String = "",
    var user_key: String = "",
    var commits: ArrayList<Commit> = ArrayList<Commit>(),
    val created_at: String = "",
    val updated_at: String = "",
    val size: Int = 0
) {
    val isSavedRes = if (isSaved) R.drawable.saved
    else R.drawable.unsaved
}
