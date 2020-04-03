package progr.rostoslav.githubapi.ui.recycler


import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import progr.rostoslav.githubapi.R
import progr.rostoslav.githubapi.entities.Rep
import progr.rostoslav.githubapi.ui.recycler.adapters.RepAdapter

class RepItemTouchHelperCallback(
    val adapter: RepAdapter,
    val swipeListener: (Rep) -> Unit
) : ItemTouchHelper.Callback() {

    private val bgRect = RectF()//все объекты создаются заранее, чтобы не нагружать методы Draw
    private val bgPaint = Paint()
    private val iconBounds = Rect()

    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ) = if (viewHolder is ItemTouchViewHolder) {
        makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
    } else {
        makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.invoke(adapter.getList()[viewHolder.adapterPosition])
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder
        ) viewHolder.onItemSelected()
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemTouchViewHolder) viewHolder.onItemCleared()
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            //   drawIcon(canvas, itemView, dX)

        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
        val icon = itemView.resources.getDrawable(R.drawable.saved, itemView.context.theme)
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.size_item_helper_icon)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spase_16)
        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBounds) {
            left = itemView.right - iconSize - space
            top = itemView.top + margin
            right = itemView.right - space
            bottom = itemView.bottom - margin
        }
        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with(bgRect) {
            left = itemView.left.toFloat() + dX
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }
        with(bgPaint) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = itemView.resources.getColor(
                    R.color.color_background_grey,
                    itemView.context.theme
                )
            }//TODO найти способ для API 21-23
        }
        canvas.drawRect(bgRect, bgPaint)
    }
}

interface ItemTouchViewHolder {
    fun onItemSelected()
    fun onItemCleared()
}