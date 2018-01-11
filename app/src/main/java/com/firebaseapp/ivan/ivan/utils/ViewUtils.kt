package com.firebaseapp.ivan.ivan.utils

import android.view.View

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