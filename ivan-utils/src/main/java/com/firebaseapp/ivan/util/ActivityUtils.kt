@file:JvmName("ActivityUtil")

package com.firebaseapp.ivan.util

import android.app.Activity
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by phompang on 12/13/2017 AD.
 */

fun Activity.toast(message: CharSequence, length: Int = Toast.LENGTH_LONG) {
	Toast.makeText(this, message, length).show()
}

fun AppCompatActivity.replaceFragmentSafely(fragment: Fragment,
											tag: String,
											allowStateLoss: Boolean = false,
											@IdRes containerViewId: Int,
											@AnimRes enterAnimation: Int = 0,
											@AnimRes exitAnimation: Int = 0,
											@AnimRes popEnterAnimation: Int = 0,
											@AnimRes popExitAnimation: Int = 0) {
	val ft = supportFragmentManager
			.beginTransaction()
			.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
			.replace(containerViewId, fragment, tag)
	if (!supportFragmentManager.isStateSaved) {
		ft.commit()
	} else if (allowStateLoss) {
		ft.commitAllowingStateLoss()
	}
}