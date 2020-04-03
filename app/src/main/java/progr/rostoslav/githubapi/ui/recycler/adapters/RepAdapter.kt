package progr.rostoslav.githubapi.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_rep.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.recycler.ItemTouchViewHolder
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseViewHolder

class RepAdapter() : BaseAdapter<Rep>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rep, parent, false),
            callback
        )

    class ViewHolder(currentView: View, val callback: BaseAdapterCallback<Rep>?) :
        BaseViewHolder<Rep>(currentView)
      , ItemTouchViewHolder {
        override fun bind(model: Rep) {
            ir_tv_title.text = model.title
            ir_tv_commits.text = "" + model.commits_count
            ir_tv_descr.text = model.description
            ir_tv_forks.text = "" + model.forks_count
            ir_tv_lang.text = "(${model.lang})"
            ir_tv_stars.text = "" + model.stars_count
            imageView.apply {
                transitionName = model.title
                Glide.with(context)
                    .load("https://sun9-59.userapi.com/c857536/v857536300/e2f7d/NN5SjYymVew.jpg")
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            }

            ir_iv_fav.setImageResource(model.isSavedRes)
            ir_iv_fav.setOnClickListener { callback?.onSavedClick(model, itemView) }
        }

        override fun onItemSelected() {
           // TODO некоторые холдеры запоминают цвет после смены контента.
             //  проконтролировать очистку и возврат к дефолтному цвету при отпускании
            //  itemView.setBackgroundResource(R.color.color_primaryDark)
        }

        override fun onItemCleared() {
            //цвет айтема после возвращения
            //       itemView.setBackgroundResource(R.color.color_primaryDark)
        }
    }


    fun updateData(data: ArrayList<Rep>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                (items[oldItemPosition].title == data[newItemPosition].title) &&
                        (items[oldItemPosition].author == data[newItemPosition].author)

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                (items[oldItemPosition].hashCode() == data[newItemPosition].hashCode())

            override fun getOldListSize() = items.size
            override fun getNewListSize() = data.size
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)


    }
    interface RepSelectedListener {
        fun onRepSelected(rep: Rep, imageView: ImageView)
    }


}