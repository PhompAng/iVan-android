package com.firebaseapp.ivan.ivan.di

import com.firebaseapp.ivan.ivan.api.MobilityApi
import com.firebaseapp.ivan.livedataadapter.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author phompang on 16/1/2018 AD.
 */

@Module
internal class AppModule {

	@Singleton @Provides
	fun provideAppLifecycleCallbacks(): AppLifecycleCallbacks = AppLifecycleCallbacks()

	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
				.baseUrl("http://35.201.251.192:3000")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(LiveDataCallAdapterFactory())
				.build()
	}

	@Singleton
	@Provides
	fun provideServerService(retrofit: Retrofit): MobilityApi {
		return retrofit.create(MobilityApi::class.java)
	}
}