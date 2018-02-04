package com.firebaseapp.ivan.ivan

import com.firebaseapp.ivan.ivan.di.AppLifecycleCallbacks
import com.firebaseapp.ivan.ivan.di.DaggerAppComponent
import com.firebaseapp.ivan.ivan.di.applyAutoInjector
import com.firebaseapp.ivan.ivan.helper.NotificationHelper
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * @author phompang on 9/1/2018 AD.
 */
class IVanApplication : DaggerApplication() {
	@Inject lateinit var appLifecycleCallBack: AppLifecycleCallbacks

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
		return DaggerAppComponent.builder().application(this).build()
	}

	override fun onCreate() {
		super.onCreate()
		applyAutoInjector()
		appLifecycleCallBack.onCreate(this)
		NotificationHelper(this)
	}

	override fun onTerminate() {
		appLifecycleCallBack.onTerminate(this)
		super.onTerminate()
	}
}