package com.firebaseapp.ivan.ivan

import android.app.Application
import com.firebaseapp.ivan.ivan.helper.NotificationHelper
import com.firebaseapp.ivan.ivan.utils.inDebugMode
import timber.log.Timber

/**
 * @author phompang on 9/1/2018 AD.
 */
class IVanApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		NotificationHelper(this)
		inDebugMode {
			Timber.plant(object : Timber.DebugTree() {
				override fun createStackElementTag(element: StackTraceElement): String? {
					return "${super.createStackElementTag(element)} : ${element.methodName} (${element.fileName}:${element.lineNumber})"
				}
			})
		}
	}
}