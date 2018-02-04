package com.wongnai.android

import android.view.View

/**
 * @author phompang on 21/1/2018 AD.
 */
interface TypeItemEventListener<in I> {
	fun onItemClick(view: View, item: I, position: Int)
}
