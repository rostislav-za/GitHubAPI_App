package progr.rostoslav.githubapi.ui.activityes

import progr.rostoslav.githubapi.Action
import progr.rostoslav.githubapi.entities.Rep

interface ActionProvider {
    fun reduce(a:Action)
    fun startLoginActivity()
}