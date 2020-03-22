package progr.rostoslav.githubapi.domain.entities


data class RepInfo(
    val title: String="",
    val lang: String="",
    val stars_count: Int=0,
    val commits_count: Int=0,
    val forks_count: Int=0,
    var isFav: Boolean = false,
    val full_name: String = "",
    val login: String = "",
    val node_id: String = "",
    val avatar_url: String = "",
    val url: String = "",
    val description: String = "",
    val created_at: String = "",
    val updated_at: String = "",
    val size: Int = 0,
    val network_count: Int = 0,
    val subscribers_count: Int = 0,
    val open_issues: Int = 0,
    val watchers: Int = 0,
    val default_branch: String = "",
    val has_issues: Boolean = false,
    val has_projects: Boolean = false,
    val has_downloads: Boolean = false,
    val has_wiki: Boolean = false,
    var commits: List<Commit> = emptyList()
)
