package progr.rostoslav.githubapi.ui.recycler.bases

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<P> : RecyclerView.Adapter<BaseViewHolder<P>>() {
    protected var items: MutableList<P> = ArrayList()
    var callback: BaseAdapterCallback<P>? = null

    var hasItems = false

    fun setList(data: List<P>) {
        items.clear()
        items.addAll(data)
        hasItems = true
        notifyDataSetChanged()
    }

    fun getList(): List<P> {
        val r = ArrayList<P>()
        r.addAll(items)
        return r
    }

    fun addItem(item: P, position: Int = items.lastIndex) {
        items.add(item)
        notifyItemInserted(position)
    }

    fun addItems(itemsForAdd: List<P>, position: Int = 0) {
        items.addAll(position, itemsForAdd)
        notifyItemRangeInserted(position, itemsForAdd.count())
    }

    fun updateItem(position: Int, newItem: P) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    fun updateItem(oldItem: P, newItem: P) {
        updateItem(items.lastIndexOf(oldItem), newItem)
    }

    fun deleteItem(item: P) {
        if(items.contains(item)){
        val position = items.indexOf(item)
        deleteItem(position)}
    }

   private fun deleteItem(position: Int) {

        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun attachCallback(callback: BaseAdapterCallback<P>) {
        this.callback = callback
    }

    fun detachCallback() {
        this.callback = null
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BaseViewHolder<P>, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            callback?.onItemClick(
                items[position],
                holder.itemView
            )
        }

        holder.itemView.setOnLongClickListener {
            if (callback == null) {
                false
            } else {
                callback!!.onLongClick(items[position], holder.itemView)
            }
        }
    }
}