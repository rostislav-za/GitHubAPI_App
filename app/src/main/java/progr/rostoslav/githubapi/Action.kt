package progr.rostoslav.githubapi

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep


sealed class Action {
    //    UI
    class UIRepClickedAction(val rep: Rep) : Action()
    class UIRepSavedChangedAction(val rep: Rep) : Action()
    class UIRefreshedListAction : Action()

    //    NETWORK
    class RepItemLoadAction(val rep: Rep) : Action()
    class RepInfoLoadedAction(val rep_info: Rep) : Action()
    class RepsLoadedAction(val new_reps: ArrayList<Rep>) : Action()
    class CommitsLoadedAction(val new_commits: ArrayList<Commit>) : Action()
}