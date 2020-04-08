package progr.rostoslav.githubapi.ui

import progr.rostoslav.githubapi.entities.Rep

interface ActionProvider {
    fun repIsSavedChanged(rep:Rep)
    fun repItemClicked(rep: Rep)
    fun refreshData()
}