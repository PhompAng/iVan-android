package com.firebaseapp.ivan.util

import android.content.Context
import android.support.annotation.DimenRes
import android.util.TypedValue

/**
 * @author phompang on 3/2/2018 AD.
 */

fun convertToPx(context: Context, dp: Int): Int {
	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
}

fun Context.getDimensionPixelSize(@DimenRes resId: Int) = this.resources.getDimensionPixelSize(resId)