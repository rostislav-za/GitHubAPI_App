package progr.rostoslav.githubapi.ui.recycler.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_task.*
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.TaskInfo
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapter
import progr.rostoslav.githubapi.ui.recycler.bases.BaseAdapterCallback
import progr.rostoslav.githubapi.ui.recycler.bases.BaseViewHolder


class TaskAdapter(): BaseAdapter<TaskInfo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false),
            callback
        )

    class ViewHolder(currentView: View, val callback: BaseAdapterCallback<TaskInfo>?) : BaseViewHolder<TaskInfo>(currentView) {
        override fun bind(model:TaskInfo) {
            it_title.text= model.title
            it_text.text= model.text
            if(model.text=="")it_text.isEnabled=false
            it_iv_checked.setImageResource( model.isReadyImg)
        }
    }

}
