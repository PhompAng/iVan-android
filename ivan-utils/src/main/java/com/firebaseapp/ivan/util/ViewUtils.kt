package com.firebaseapp.ivan.util

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author phompang on 11/1/2018 AD.
 */

fun View.isVisible() = visibility == View.VISIBLE

fun View.show() {
	visibility = View.VISIBLE
}

fun View.hide() {
	visibility = View.GONE
}

fun View.invisible() {
	visibility = View.INVISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
	return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T : ViewDataBinding> ViewGroup.inflateBinding(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): T {
	return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot) as T
}