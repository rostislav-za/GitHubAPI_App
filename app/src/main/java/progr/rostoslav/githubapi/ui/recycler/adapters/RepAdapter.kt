package progr.rostoslav.githubapi.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseViewHolder

class RepAdapter() : BaseAdapter<Rep>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rep, parent, false),
            callback)

    class ViewHolder(currentView: View, val callback: BaseAdapterCallback<Rep>?) : BaseViewHolder<Rep>(currentView) {
        override fun bind(model: Rep) {
            ir_tv_title.text = model.title
            ir_tv_commits.text = "" + model.commits_count
            ir_tv_descr.text = "(${model.lang})${model.description}"
            ir_tv_forks.text = "" + model.forks_count
            ir_authorname.text = "${model.author}/"
            ir_tv_stars.text = "" + model.stars_count
            imageView.apply {
                transitionName = model.title
                Glide.with(context)
                    .load(model.autor_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            }
            ir_iv_fav.setImageResource(model.isSavedRes)
            ir_iv_fav.setOnClickListener { callback?.onSavedClick(model, itemView) }
        }
    }
}