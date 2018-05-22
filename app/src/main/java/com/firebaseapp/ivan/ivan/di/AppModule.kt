package com.firebaseapp.ivan.ivan.di

import com.firebaseapp.ivan.ivan.api.AlarmStatusApi
import com.firebaseapp.ivan.ivan.api.MobilityApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



/**
 * @author phompang on 16/1/2018 AD.
 */

@Module(includes = [ViewModelModule::class])
internal class AppModule {

	@Singleton @Provides
	fun provideAppLifecycleCallbacks(): AppLifecycleCallbacks = AppLifecycleCallbacks()

	@Singleton
	@Provides
	fun provideOkHttp(): OkHttpClient {
		val interceptor = HttpLoggingInterceptor()
		interceptor.level = HttpLoggingInterceptor.Level.BODY
		return OkHttpClient.Builder().addInterceptor(interceptor).build()
	}

	@Singleton
	@Provides
	fun provideRetrofit(client: OkHttpClient): Retrofit {
		return Retrofit.Builder()
				.client(client)
				.baseUrl("http://35.201.251.192:3000")
				.addConverterFactory(GsonConverterFactory.create())
				.build()
	}

	@Singleton
	@Provides
	fun provideServerService(retrofit: Retrofit): MobilityApi {
		return retrofit.create(MobilityApi::class.java)
	}

	@Singleton
	@Provides
	fun provideAlarmStatusService(retrofit: Retrofit): AlarmStatusApi {
		return retrofit.create(AlarmStatusApi::class.java)
	}
}