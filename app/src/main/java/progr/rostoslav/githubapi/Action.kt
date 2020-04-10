package progr.rostoslav.githubapi

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepInfo

sealed class Action {
    //    UI
    class UIRepClickedAction(val rep: Rep) : Action()
    class UIRepSavedChangedAction(val rep: Rep) : Action()
    class UIRefreshedListAction : Action()

    //    NETWORK
    class RepItemLoadAction(val rep: Rep) : Action()
    class RepInfoLoadedAction(val rep_info: RepInfo) : Action()
    class RepsLoadedAction(val new_reps: ArrayList<Rep>) : Action()
    class CommitsLoadedAction(val new_commits: ArrayList<Commit>) : Action()
}