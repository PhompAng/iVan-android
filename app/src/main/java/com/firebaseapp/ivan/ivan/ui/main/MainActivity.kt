package com.firebaseapp.ivan.ivan.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.firebaseapp.ivan.ivan.EXTRA_UID
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.ui.driver.DriverActivity
import com.firebaseapp.ivan.ivan.ui.carmap.CarMapFragment
import com.firebaseapp.ivan.ivan.ui.notification.NotificationFragment
import com.firebaseapp.ivan.ivan.ui.parent.ParentActivity
import com.firebaseapp.ivan.ivan.ui.select.SelectCarFragment
import com.firebaseapp.ivan.ivan.ui.students.StudentsFragment
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.*
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.CIRCLE
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector, SelectCarFragment.SelectCarCallback {
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>
	private lateinit var uid: String
	private lateinit var viewModel: MainViewModel

	companion object {
		const val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

		fun createIntent(ctx: Context, uid: String): Intent = Intent(ctx, MainActivity::class.java).apply {
			putExtra(EXTRA_UID, uid)
		}
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)
		viewModel = obtainViewModel(MainViewModel::class.java)

		when (savedInstanceState) {
			null -> {
				extractExtras(intent.extras)
				replaceFragment(R.id.nav_select_car)
			}
			else -> extractExtras(savedInstanceState)
		}

		requestPermissions()
		setUpDrawer()

		viewModel.setUid(this.uid)
		viewModel.getParent().observe(this) {
			it ?: return@observe
			IVan.setUser(applicationContext, it)
			DataBindingUtils.loadFromFirebaseStorage(userThumbnailImageView, it, getDrawable(R.mipmap.ic_launcher_round), CIRCLE)
			userNameTextView.text = it.fullName()
			emailTextView.text = it.email
		}

		if (IVan.getCarNullable(applicationContext) == null) {
			drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
		}
	}

	private fun extractExtras(bundle: Bundle) {
		uid = bundle.getString(EXTRA_UID)
	}

	private fun requestPermissions() {
		val permissionsNeeded = mutableListOf<String>()
		if (!applicationContext.gotPermission(Manifest.permission.CALL_PHONE)) {
			permissionsNeeded += Manifest.permission.CALL_PHONE
		}
		if (!applicationContext.gotPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			permissionsNeeded += Manifest.permission.WRITE_EXTERNAL_STORAGE
		}
		if (!applicationContext.gotPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
			permissionsNeeded += Manifest.permission.ACCESS_FINE_LOCATION
		}

		if (permissionsNeeded.size > 0) {
			requestPermissions(permissionsNeeded.toTypedArray(), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
		}
	}

	private fun setUpDrawer() {
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()

		nav_view.setNavigationItemSelectedListener(this)
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putString(EXTRA_UID, uid)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)
		savedInstanceState?.let {
			extractExtras(it)
		}
	}

	override fun onBackPressed() {
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		when (requestCode) {
			REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
				val perms = mutableMapOf<String, Int>()
				for (i in permissions.indices) {
					perms[permissions[i]] = grantResults[i]
				}

				for ((key, value) in perms) {
					if (value != PackageManager.PERMISSION_GRANTED) {
						toast("$key is not granted.")
						finish()
					}
				}
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		Timber.d(item.itemId.toString())
		replaceFragment(item.itemId)

		drawer_layout.closeDrawer(GravityCompat.START)
		return true
	}

	private fun replaceFragment(id: Int) {
		val fragment: Fragment
		val tag: String
		when (id) {
			R.id.nav_select_car -> {
				fragment = SelectCarFragment.newInstance(uid)
				tag = SelectCarFragment.TAG
			}
			R.id.nav_car_tracking -> {
				fragment = CarMapFragment.newInstance()
				tag = CarMapFragment.TAG
			}
			R.id.nav_student_list -> {
				fragment = StudentsFragment.newInstance()
				tag = StudentsFragment.TAG
			}
			R.id.nav_driver -> {
				startActivity<DriverActivity>(DriverActivity.EXTRA_DRIVER_ID to IVan.getCar(applicationContext).drivers[0].getKeyOrId())
				return
			}
			R.id.nav_profile -> {
				startActivity<ParentActivity>(ParentActivity.EXTRA_PARENT_ID to IVan.getUser(applicationContext).getKeyOrId())
				return
			}
			R.id.nav_notification -> {
				fragment = NotificationFragment.newInstance()
				tag = NotificationFragment.TAG
			}
			else -> {
				return
			}
		}
		replaceFragmentSafely(fragment, tag, true, R.id.flContent)
	}

	override fun onCarSelect() {
		drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
		replaceFragment(R.id.nav_car_tracking)
	}
}
