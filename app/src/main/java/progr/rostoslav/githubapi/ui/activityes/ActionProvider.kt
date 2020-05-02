package progr.rostoslav.githubapi.ui.activityes

import progr.rostoslav.githubapi.domain.Action

interface ActionProvider {
    fun reduce(a: Action)
    fun startLoginActivity()
}