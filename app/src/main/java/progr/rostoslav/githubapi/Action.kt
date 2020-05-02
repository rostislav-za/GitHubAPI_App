package progr.rostoslav.githubapi

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.entities.Rep


sealed class Action {
    //    UI
    class UIRepClickedAction(val rep: Rep) : Action()
    class UIRepSavedChangedAction(val rep: Rep) : Action()
    class UIRefreshedListAction : Action()

    //    NETWORK
    class NWRepItemLoadAction(val rep: Rep) : Action()
    class NWRepsLoadedAction(val new_reps: ArrayList<Rep>) : Action()
    class NWCommitsLoadedAction(val new_commits: ArrayList<Commit>) : Action()
}