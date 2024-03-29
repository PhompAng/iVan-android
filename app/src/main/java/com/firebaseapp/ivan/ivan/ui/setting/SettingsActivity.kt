package com.firebaseapp.ivan.ivan.ui.setting

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.preference.*
import android.view.MenuItem
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.helper.LocaleHelper
import java.util.*


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : AppCompatPreferenceActivity(), OnLocaleChangedListener {
	private val localizationDelegate = LocalizationActivityDelegate(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		localizationDelegate.addOnLocaleChangedListener(this)
		localizationDelegate.onCreate(savedInstanceState)
		super.onCreate(savedInstanceState)
		setupActionBar()
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, GeneralPreferenceFragment())
				.commit()
	}

	override fun onResume() {
		super.onResume()
		localizationDelegate.onResume(this)
	}


	override fun attachBaseContext(newBase: Context) {
		super.attachBaseContext(localizationDelegate.attachBaseContext(newBase))
	}

	override fun getApplicationContext(): Context {
		return localizationDelegate.getApplicationContext(super.getApplicationContext())
	}

	override fun getResources(): Resources {
		return localizationDelegate.getResources(super.getResources())
	}

	fun setLanguage(language: String) {
		localizationDelegate.setLanguage(this, language)
	}

	fun setLanguage(locale: Locale) {
		localizationDelegate.setLanguage(this, locale)
	}

	fun setDefaultLanguage(language: String) {
		localizationDelegate.setDefaultLanguage(language)
	}

	fun setDefaultLanguage(locale: Locale) {
		localizationDelegate.setDefaultLanguage(locale)
	}

	fun getCurrentLanguage(): Locale {
		return localizationDelegate.getLanguage(this)
	}

	// Just override method locale change event
	override fun onBeforeLocaleChanged() {}

	override fun onAfterLocaleChanged() {}

	/**
	 * Set up the [android.app.ActionBar], if the API is available.
	 */
	private fun setupActionBar() {
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	/**
	 * This method stops fragment injection in malicious applications.
	 * Make sure to deny any unknown fragments here.
	 */
	override fun isValidFragment(fragmentName: String): Boolean {
		return PreferenceFragment::class.java.name == fragmentName
				|| GeneralPreferenceFragment::class.java.name == fragmentName
	}

	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	class GeneralPreferenceFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			addPreferencesFromResource(R.xml.pref_general)
			setHasOptionsMenu(true)

			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
			bindPreferenceSummaryToValue(findPreference("preference_lang"))
		}

		override fun onResume() {
			super.onResume()
			preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

		}

		override fun onPause() {
			preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
			super.onPause()
		}

		override fun onOptionsItemSelected(item: MenuItem): Boolean {
			val id = item.itemId
			if (id == android.R.id.home) {
				startActivity(Intent(activity, SettingsActivity::class.java))
				return true
			}
			return super.onOptionsItemSelected(item)
		}

		override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
			if (key.equals("preference_lang")) {
				if (activity is SettingsActivity) {
					LocaleHelper.getLanguage(context)?.let {
						(activity as? SettingsActivity)?.setLanguage(it)
					}
				}
			}
		}
	}

	companion object {

		/**
		 * A preference value change listener that updates the preference's summary
		 * to reflect its new value.
		 */
		private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
			val stringValue = value.toString()

			if (preference is ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				val listPreference = preference
				val index = listPreference.findIndexOfValue(stringValue)

				// Set the summary to reflect the new value.
				preference.setSummary(
						if (index >= 0)
							listPreference.entries[index]
						else
							null)

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.summary = stringValue
			}
			true
		}

		/**
		 * Binds a preference's summary to its value. More specifically, when the
		 * preference's value is changed, its summary (line of text below the
		 * preference title) is updated to reflect the value. The summary is also
		 * immediately updated upon calling this method. The exact display format is
		 * dependent on the type of preference.

		 * @see .sBindPreferenceSummaryToValueListener
		 */
		private fun bindPreferenceSummaryToValue(preference: Preference) {
			// Set the listener to watch for value changes.
			preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

			// Trigger the listener immediately with the preference's
			// current value.
			sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
					PreferenceManager
							.getDefaultSharedPreferences(preference.context)
							.getString(preference.key, ""))
		}
	}
}
