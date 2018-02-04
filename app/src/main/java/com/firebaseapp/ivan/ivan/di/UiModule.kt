package com.firebaseapp.ivan.ivan.di

import android.arch.lifecycle.ViewModelProvider
import com.firebaseapp.ivan.ivan.ui.main.MainActivity
import com.firebaseapp.ivan.ivan.ui.main.MainModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 16/1/2018 AD.
 */
@Module
internal abstract class UiModule {
	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@ContributesAndroidInjector(modules = [MainModule::class])
	internal abstract fun contributeMainActivity(): MainActivity
}