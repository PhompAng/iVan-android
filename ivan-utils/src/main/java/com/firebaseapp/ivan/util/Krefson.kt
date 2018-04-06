package com.firebaseapp.ivan.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * @author phompang on 21/1/2018 AD.
 */
open class Krefson(context: Context,
				   val name: String = DEFAULT_NAME,
				   val gson: Gson = Gson()){
	companion object {
		const val DEFAULT_NAME = "default"

		const val KEY_PARENT = "key-parent"
		const val KEY_DRIVER = "key-driver"
		const val KEY_TEACHER = "key-teacher"
		const val KEY_CAR = "key-car"
	}
	val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

	inline operator fun <reified T> get(key: String): T? {
		val value = sharedPreferences.getString(key, null)
		if (value == null) {
			return value
		}
		return gson.fromJson(value, T::class.java)
	}

	inline operator fun <reified T> get(key: String, default: T): T {
		return gson.fromJson(sharedPreferences.getString(key, null), T::class.java) ?: default
	}

	inline operator fun <reified T> set(key: String, value: T) {
		sharedPreferences.edit().putString(key, gson.toJson(value)).apply()
	}

	operator fun contains(key: String): Boolean {
		return sharedPreferences.contains(key)
	}

	fun remove(key: String) {
		sharedPreferences.edit().remove(key).apply()
	}

	fun clear() {
		sharedPreferences.edit().clear().apply()
	}
}