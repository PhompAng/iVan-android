package com.firebaseapp.ivan.ivan.ui.teacher

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 17/4/2018 AD.
 */
@Module
internal abstract class TeacherModule {

	@ContributesAndroidInjector
	abstract fun contributeTeacherFragment(): TeacherFragment
}