package com.firebaseapp.ivan.ivan.ui.driver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.NONE
import com.firebaseapp.ivan.util.replaceFragmentSafely
import kotlinx.android.synthetic.main.collapsing_toolbar_main.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class DriverActivity : AppCompatActivity() {
	private val driver by lazy {
		IVan.getCar(applicationContext).drivers[0]
	}

	companion object {
		const val EXTRA_DRIVER_ID = "extra-driver-id"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.collapsing_toolbar_main)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		when (savedInstanceState) {
			null -> {
				replaceFragmentSafely(
						DriverFragment(),
						DriverFragment.TAG,
						true,
						R.id.flContent
				)
			}
		}
		collapsingToolbarLayout.title = driver.fullName()
		DataBindingUtils.loadFromFirebaseStorage(headerImageView, driver, getDrawable(R.color.colorPrimary), NONE)
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