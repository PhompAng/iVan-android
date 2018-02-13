package com.firebaseapp.ivan.ivan.di

import com.firebaseapp.ivan.ivan.ui.driver.DriverActivity
import com.firebaseapp.ivan.ivan.ui.driver.DriverModule
import com.firebaseapp.ivan.ivan.ui.main.MainActivity
import com.firebaseapp.ivan.ivan.ui.main.MainModule
import com.firebaseapp.ivan.ivan.ui.student.StudentActivity
import com.firebaseapp.ivan.ivan.ui.student.StudentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 16/1/2018 AD.
 */
@Module
internal abstract class UiModule {
	@ContributesAndroidInjector(modules = [MainModule::class])
	internal abstract fun contributeMainActivity(): MainActivity

	@ContributesAndroidInjector(modules = [StudentModule::class])
	internal abstract fun contributeStudentActivity(): StudentActivity

	@ContributesAndroidInjector(modules = [DriverModule::class])
	internal abstract fun contributeDriverActivity(): DriverActivity
}