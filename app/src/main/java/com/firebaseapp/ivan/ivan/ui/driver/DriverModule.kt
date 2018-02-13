package com.firebaseapp.ivan.ivan.ui.driver

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 13/2/2018 AD.
 */
@Module
internal abstract class DriverModule {
	@ContributesAndroidInjector
	abstract fun contributeDriverFragment(): DriverFragment
}