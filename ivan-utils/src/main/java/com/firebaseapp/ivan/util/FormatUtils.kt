package com.firebaseapp.ivan.util

import android.content.Context

/**
 * @author phompang on 8/3/2018 AD.
 */

fun formatDistance(context: Context, distance: Float?): String = when (distance) {
	null -> "-"
	else -> context.getString(R.string.distance_value, distance / 1000)
}

fun formatTime(context: Context, time: Int?): String = when (time) {
	null -> "-"
	else -> context.getString(R.string.estimate_value, time / 60)
}
