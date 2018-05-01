package com.firebaseapp.ivan.ivan.helper

import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences
import java.util.*


/**
 * @author phompang on 1/5/2018 AD.
 */
object LocaleHelper {
	private const val SELECTED_LANGUAGE = "preference_lang"

	fun getLanguage(context: Context): String? {
		return getPersistedData(context, Locale.getDefault().language)
	}

	private fun getPersistedData(context: Context, defaultLanguage: String): String? {
		val preferences = PreferenceManager.getDefaultSharedPreferences(context)
		return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
	}
}