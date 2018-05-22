package com.firebaseapp.ivan.ivan.di

import com.firebaseapp.ivan.ivan.IVanApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author phompang on 16/1/2018 AD.
 */

@Singleton
@Component(modules = [
	AndroidSupportInjectionModule::class,
	AppModule::class,
	UiModule::class
])
interface AppComponent : AndroidInjector<IVanApplication> {

	@Component.Builder
	interface Builder {
		@BindsInstance
		fun application(application: IVanApplication): Builder

		fun build(): AppComponent
	}

	override fun inject(instance: IVanApplication?)
}