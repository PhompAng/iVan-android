package com.firebaseapp.ivan.ivan.ui.alarmstatus

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.util.replaceFragmentSafely
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

/**
 * @author phompang on 13/4/2018 AD.
 */
class AlarmStatusActivity : AppCompatActivity(), HasSupportFragmentInjector {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

	private lateinit var uid: String

	companion object {
		const val EXTRA_UID = "extra_uid"
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.app_bar_main)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		when (savedInstanceState) {
			null -> {
				extractExtras(intent.extras)
				replaceFragmentSafely(
						AlarmStatusFragment.newInstance(uid),
						AlarmStatusFragment.TAG,
						true,
						R.id.flContent
				)
			}
			else -> extractExtras(savedInstanceState)
		}
	}

	private fun extractExtras(bundle: Bundle) {
		uid = bundle.getString(EXTRA_UID)
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putString(EXTRA_UID, uid)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item?.itemId) {
			android.R.id.home -> {
				finish()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}