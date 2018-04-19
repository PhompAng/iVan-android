package com.firebaseapp.ivan.ivan.ui.parent

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 17/4/2018 AD.
 */
@Module
internal abstract class ParentModule {

	@ContributesAndroidInjector
	abstract fun contributeParentFragment(): ParentFragment
}