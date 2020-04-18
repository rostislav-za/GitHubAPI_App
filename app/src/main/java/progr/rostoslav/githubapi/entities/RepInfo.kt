package progr.rostoslav.githubapi.entities

import progr.rostoslav.githubapi.R

data class RepInfo(
    val title: String="",
    val lang: String="",
    val stars_count: Int=0,
    val commits_count: Int=0,
    val forks_count: Int=0,
    var isSaved: Boolean = false,
    val full_name: String = "",
    val login: String = "",
    val avatar_url: String = "",
    val description: String = "",
    val created_at: String = "",
    val updated_at: String = "",
    val size: Int = 0,
    var commits: List<Commit> = emptyList()
){
    val isSavedRes = if (isSaved) R.drawable.saved
    else R.drawable.unsaved
}
