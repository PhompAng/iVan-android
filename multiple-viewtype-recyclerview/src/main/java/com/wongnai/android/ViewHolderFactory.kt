package com.wongnai.android

import android.view.ViewGroup

/**
 * @author phompang on 21/1/2018 AD.
 */

interface ViewHolderFactory<T> {
	fun create(parent: ViewGroup?): ItemViewHolder<*>
}