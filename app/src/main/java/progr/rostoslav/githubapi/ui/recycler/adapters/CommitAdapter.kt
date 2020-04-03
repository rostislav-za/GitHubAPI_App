package progr.rostoslav.githubapi.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_commit.*
import progr.rostoslav.githubapi.R

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseViewHolder


class CommitAdapter(): BaseAdapter<Commit>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false),
            callback
        )

    class ViewHolder(currentView: View, val callback: BaseAdapterCallback<Commit>?) : BaseViewHolder<Commit>(currentView) {
        override fun bind(model: Commit) {
            ic_tv_commit.text= model.title
        }
    }

}
