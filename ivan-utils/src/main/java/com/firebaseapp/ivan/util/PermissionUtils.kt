package com.firebaseapp.ivan.util

import android.content.Context
import android.content.pm.PackageManager

/**
 * @author phompang on 19/2/2018 AD.
 */

fun Context.gotPermission(permission: String): Boolean {
	return this.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}