package com.firebaseapp.ivan.ivan.ui.main

import com.firebaseapp.ivan.ivan.ui.map.CarMapFragment
import com.firebaseapp.ivan.ivan.ui.select.SelectCarFragment
import com.firebaseapp.ivan.ivan.ui.students.StudentsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 16/1/2018 AD.
 */
/**
 * @author phompang on 16/1/2018 AD.
 */
@Module
internal abstract class MainModule {

	@ContributesAndroidInjector
	abstract fun contributeSelectCarFragment(): SelectCarFragment

	@ContributesAndroidInjector
	abstract fun contributeCarMapFragment(): CarMapFragment

	@ContributesAndroidInjector
	abstract fun contributeStudentsFragment(): StudentsFragment
}