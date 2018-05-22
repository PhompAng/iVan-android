package com.firebaseapp.ivan.ivan.ui.student

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 9/2/2018 AD.
 */
@Module
internal abstract class StudentModule {
	@ContributesAndroidInjector
	abstract fun contributeStudentFragment(): StudentFragment
}