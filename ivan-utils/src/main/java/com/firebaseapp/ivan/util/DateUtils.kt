package com.firebaseapp.ivan.util

import android.content.Context
import android.text.format.DateUtils

/**
 * @author phompang on 4/2/2018 AD.
 */

fun Long.getRelativeTime(context: Context): CharSequence =
		DateUtils.getRelativeTimeSpanString(context, this)