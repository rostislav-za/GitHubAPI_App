package progr.rostoslav.githubapi.data.local

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep


class FakeData {
    val langs = listOf<String>("Java", "C++", "Kotlin", "C#", "Python", "C", "Dart")

    fun getCommits(count: Int): List<Commit> {
        var r = ArrayList<Commit>()
        for (i in 0..count) r.add(
            Commit(
                "HardCommit № $i","https://avatars0.githubusercontent.com/u/17958266?v=4","Rstislav_hardIni","2019-01-25T03:43:57Z"
            )
        )
        return r
    }

    fun getRepositoryes(count: Int): List<Rep> {
        var r = ArrayList<Rep>()
        for (i in 0..count)r.add(
            Rep(
                title = "Repository name  $i",
                description = "This is a god project",
                lang = langs[i % 7],
                forks_count = i * (i % 2) + 2 * i + 7,
                stars_count = i * (i % 4) + i,
                commits_count = i * (i % 2) + i,
                isSaved = i % 3 == 0
            ))

        return r
    }


}