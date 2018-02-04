package com.firebaseapp.ivan.ivan.di

import android.app.Application
import android.util.Log
import timber.log.Timber

/**
 * @author phompang on 16/1/2018 AD.
 */
class AppLifecycleCallbacks {
	fun onCreate(application: Application) {
		Timber.plant(object : Timber.DebugTree() {
			override fun createStackElementTag(element: StackTraceElement): String? {
				return "${super.createStackElementTag(element)} : ${element.methodName} (${element.fileName}:${element.lineNumber})"
			}
		})
	}

	fun onTerminate(application: Application) {
	}
}