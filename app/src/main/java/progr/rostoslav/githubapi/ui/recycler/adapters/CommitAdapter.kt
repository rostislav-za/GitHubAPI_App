package progr.rostoslav.githubapi.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_commit.*
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.R

import progr.rostoslav.githubapi.entities.Commit
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseViewHolder


class CommitAdapter() : BaseAdapter<Commit>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false),
            callback
        )

    class ViewHolder(currentView: View, val callback: BaseAdapterCallback<Commit>?) :
        BaseViewHolder<Commit>(currentView) {
        override fun bind(model: Commit) {
            ic_tv_commit.text = model.title
            ic_tv_date.text = "commited an ${model.date.substring(0, 10)}"
            ic_tv_author.text = model.author
            ic_iv_author_img.apply {
                Glide.with(context)
                    .load(model.author_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            }
        }
    }

}