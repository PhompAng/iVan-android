package com.wongnai.android

import android.content.Context
import android.databinding.ViewDataBinding

/**
 * @author phompang on 21/1/2018 AD.
 */
abstract class DataBindingItemViewHolder<in T>(private val binding: ViewDataBinding) : ItemViewHolder<T>(binding.root) {
	override fun getContext(): Context = binding.root.context

	override fun fillData(data: T, position: Int) {}
}