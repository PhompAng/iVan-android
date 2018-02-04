package com.firebaseapp.ivan.util

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

/**
 * @author phompang on 21/1/2018 AD.
 */
open class Krefson(context: Context,
				   val name: String = DEFAULT_NAME,
				   val moshi: Moshi = Moshi.Builder()
						   .add(KotlinJsonAdapterFactory()).build()) {
	companion object {
		const val DEFAULT_NAME = "default"

		const val KEY_PARENT = "key-parent"
		const val KEY_CAR = "key-car"
	}
	val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

	inline operator fun <reified T> get(key: String): T? {
		return moshi.adapter<T>(T::class.java).fromJson(sharedPreferences.getString(key, null))
	}

	inline operator fun <reified T> get(key: String, default: T): T {
		return moshi.adapter(T::class.java).fromJson(sharedPreferences.getString(key, null)) ?: default
	}

	inline operator fun <reified T> set(key: String, value: T) {
		sharedPreferences.edit().putString(key, moshi.adapter(T::class.java).toJson(value)).apply()
	}

	operator fun contains(key: String): Boolean {
		return sharedPreferences.contains(key)
	}

	fun remove(key: String) {
		sharedPreferences.edit().remove(key).apply()
	}
}