package com.example.easyreader3.presentation.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(currentView: View): RecyclerView.ViewHolder(currentView),LayoutContainer {
    override val containerView: View?
        get() = itemView
    abstract fun bind(model: T)
}