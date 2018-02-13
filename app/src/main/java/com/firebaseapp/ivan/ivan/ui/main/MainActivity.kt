package com.firebaseapp.ivan.ivan.ui.main

import android.content.Context
import android.content.Intent
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
import com.firebaseapp.ivan.ivan.ui.driver.DriverActivity
import com.firebaseapp.ivan.ivan.ui.map.CarMapFragment
import com.firebaseapp.ivan.ivan.ui.select.SelectCarFragment
import com.firebaseapp.ivan.ivan.ui.students.StudentsFragment
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.replaceFragmentSafely
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector, SelectCarFragment.SelectCarCallback {
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>
	private lateinit var uid: String
	private lateinit var viewModel: MainViewModel

	companion object {
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
			null -> extractExtras(intent.extras)
			else -> extractExtras(savedInstanceState)
		}

		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()

		nav_view.setNavigationItemSelectedListener(this)

		viewModel.setUid(this.uid)
		viewModel.getParent().observe(this) {
			it ?: return@observe
			IVan.setUser(applicationContext, it)
		}
		replaceFragment(R.id.nav_select_car)

		if (IVan.getCarNullable(applicationContext) == null) {
			drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
		}
	}

	private fun extractExtras(bundle: Bundle) {
		uid = bundle.getString(EXTRA_UID)
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putString(EXTRA_UID, uid)
	}

	override fun onBackPressed() {
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
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
