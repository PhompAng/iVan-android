package com.wongnai.android

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author phompang on 3/2/2018 AD.
 */

abstract class ItemViewHolder<in T>(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
	open fun getContext(): Context = containerView.context
	open fun fillData(data: T, position: Int) {}
}