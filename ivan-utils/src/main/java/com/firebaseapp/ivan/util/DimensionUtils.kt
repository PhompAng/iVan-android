package com.firebaseapp.ivan.util

import android.content.Context
import android.util.TypedValue

/**
 * @author phompang on 3/2/2018 AD.
 */

fun convertToPx(context: Context, dp: Int): Int {
	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
}