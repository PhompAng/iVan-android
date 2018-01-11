package com.firebaseapp.ivan.ivan.utils

import com.firebaseapp.ivan.ivan.BuildConfig

/**
 * @author phompang on 9/1/2018 AD.
 */

inline fun inDebugMode(func: () -> Unit) {
	if (BuildConfig.BUILD_TYPE == "debug") {
		func()
	}
}