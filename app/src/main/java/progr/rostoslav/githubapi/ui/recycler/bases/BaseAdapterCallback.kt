package com.example.easyreader3.presentation.adapters.base

import android.view.View

interface BaseAdapterCallback<T> {
    fun removeItem(model: T, view: View){}
    fun onItemClick(model: T, view: View)
    fun onLongClick(model: T, view: View): Boolean {return true}
    fun onSavedClick(model: T, view: View){}
}
