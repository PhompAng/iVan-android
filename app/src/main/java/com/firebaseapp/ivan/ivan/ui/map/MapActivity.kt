package com.firebaseapp.ivan.ivan.ui.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Location
import com.firebaseapp.ivan.util.replaceFragmentSafely
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * @author phompang on 19/2/2018 AD.
 */
class MapActivity : AppCompatActivity() {

	private lateinit var location: Location

	companion object {
		const val EXTRA_LOCATION = "extra-location"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.app_bar_main)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		when (savedInstanceState) {
			null -> {
				extractExtras(intent.extras)
				replaceFragmentSafely(
						MapFragment.newInstance(location),
						MapFragment.TAG,
						true,
						R.id.flContent
				)
			}
		}
	}

	private fun extractExtras(bundle: Bundle) {
		location = bundle.getParcelable(EXTRA_LOCATION)
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

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putParcelable(EXTRA_LOCATION, location)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)
		savedInstanceState?.let {
			extractExtras(it)
		}
	}
}