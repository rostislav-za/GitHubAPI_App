package progr.rostoslav.githubapi

import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.entities.RepInfo

sealed class Action {
    class UIRepClickedAction(val rep:Rep): Action()
    class UIRepSavedChangedAction(val rep:Rep ):
        Action()
    class RepInfoLoadedAction(val rep_info: RepInfo):
        Action()
    class RepsLoadedAction(val new_reps:ArrayList<Rep>):
        Action()
    class UIRefreshedListAction: Action()
//    class ItemClickedAction:Action()


}