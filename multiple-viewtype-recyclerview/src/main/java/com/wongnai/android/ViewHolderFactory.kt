package com.wongnai.android

import android.view.ViewGroup

/**
 * @author phompang on 21/1/2018 AD.
 */

interface ViewHolderFactory<in T> {
	fun create(parent: ViewGroup?): ItemViewHolder<T>
}