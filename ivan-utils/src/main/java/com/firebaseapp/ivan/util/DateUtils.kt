package com.firebaseapp.ivan.util

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author phompang on 4/2/2018 AD.
 */

fun Long.getRelativeTime(context: Context): CharSequence =
		DateUtils.getRelativeTimeSpanString(context, this)

fun Long.getFormattedTime(formattedString: String): String {
	val date = Date(this)
	val format = SimpleDateFormat(formattedString, Locale.getDefault())
	return format.format(date)
}