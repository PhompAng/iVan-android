@file:JvmName("ActivityUtil")

package com.firebaseapp.ivan.ivan.utils

import android.app.Activity
import android.widget.Toast

/**
 * Created by phompang on 12/13/2017 AD.
 */

fun Activity.toast(message: CharSequence, length: Int = Toast.LENGTH_LONG) {
	Toast.makeText(this, message, length).show()
}
