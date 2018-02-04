package com.firebaseapp.ivan.util

/**
 * @author phompang on 9/1/2018 AD.
 */

inline fun inDebugMode(func: () -> Unit) {
	if (BuildConfig.BUILD_TYPE == "debug") {
		func()
	}
}