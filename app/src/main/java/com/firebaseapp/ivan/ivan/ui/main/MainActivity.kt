package com.firebaseapp.ivan.ivan.ui.main

import android.Manifest
import android.annotation.TargetApi
import android.arch.lifecycle.ViewModelProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.customtabs.*
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Role
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.model.listDeserializer
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.ui.carmap.CarMapFragment
import com.firebaseapp.ivan.ivan.ui.driver.DriverActivity
import com.firebaseapp.ivan.ivan.ui.login.LoginActivity
import com.firebaseapp.ivan.ivan.ui.notification.NotificationFragment
import com.firebaseapp.ivan.ivan.ui.parent.ParentActivity
import com.firebaseapp.ivan.ivan.ui.select.SelectCarFragment
import com.firebaseapp.ivan.ivan.ui.setting.SettingsActivity
import com.firebaseapp.ivan.ivan.ui.students.StudentsFragment
import com.firebaseapp.ivan.ivan.ui.teacher.TeacherActivity
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.*
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.CIRCLE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : LocalizationActivity(), NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector, SelectCarFragment.SelectCarCallback {

	@Inject lateinit var viewModelFactory: ViewModelProvider.Factory
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>
	private lateinit var viewModel: MainViewModel
	private val user by lazy {
		IVan.getUser(this)
	}

	private var customTabsConnection: CustomTabsServiceConnection? = null
	private var customTabsSession: CustomTabsSession? = null

	companion object {
		const val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124
		const val REQUEST_CODE_ASK_OVERLAY_PERMISSION = 125

		fun createIntent(ctx: Context): Intent = Intent(ctx, MainActivity::class.java)
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)
		viewModel = obtainViewModel(viewModelFactory, MainViewModel::class.java)

		requestPermissions()
		requestOverlayPermission()
		setUpDrawer()

		when (savedInstanceState) {
			null -> {
				initFragment()
			}
		}

		setNavigationHeader()
		if (IVan.getCarNullable(applicationContext) == null) {
			drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
		}
		connectCustomTabsService()
	}

	private fun initFragment() {
		user.fold {
			onParent {
				replaceFragment(R.id.nav_select_car)
			}
			onDriver {
				setCar(it.getKeyOrId(), Role.DRIVER)
			}
			onTeacher {
				setCar(it.getKeyOrId(), Role.TEACHER)
			}
		}
	}

	private fun setNavigationHeader() {
		user.fold {
			onParent {
				nav_view.menu.setGroupVisible(R.id.parentMenu, true)
				nav_view.menu.setGroupVisible(R.id.driverMenu, false)
				nav_view.menu.setGroupVisible(R.id.teacherMenu, false)
				DataBindingUtils.loadFromFirebaseStorage(nav_view.getHeaderView(0).userThumbnailImageView, it, getDrawable(R.mipmap.ic_launcher_round), CIRCLE)
				nav_view.getHeaderView(0).userNameTextView.text = getString(R.string.name_and_role, it.fullName(), "Parent")
				nav_view.getHeaderView(0).emailTextView.text = it.email
			}
			onDriver {
				nav_view.menu.setGroupVisible(R.id.parentMenu, false)
				nav_view.menu.setGroupVisible(R.id.driverMenu, true)
				nav_view.menu.setGroupVisible(R.id.teacherMenu, false)
				DataBindingUtils.loadFromFirebaseStorage(nav_view.getHeaderView(0).userThumbnailImageView, it, getDrawable(R.mipmap.ic_launcher_round), CIRCLE)
				nav_view.getHeaderView(0).userNameTextView.text = getString(R.string.name_and_role, it.fullName(), "Driver")
				nav_view.getHeaderView(0).emailTextView.text = it.email
			}
			onTeacher {
				nav_view.menu.setGroupVisible(R.id.parentMenu, false)
				nav_view.menu.setGroupVisible(R.id.driverMenu, false)
				nav_view.menu.setGroupVisible(R.id.teacherMenu, true)
				DataBindingUtils.loadFromFirebaseStorage(nav_view.getHeaderView(0).userThumbnailImageView, it, getDrawable(R.mipmap.ic_launcher_round), CIRCLE)
				nav_view.getHeaderView(0).userNameTextView.text = getString(R.string.name_and_role, it.fullName(), "Teacher")
				nav_view.getHeaderView(0).emailTextView.text = it.email
			}
		}
	}

	private fun setCar(uid: String, role: Int) {
		FirebaseDatabase.getInstance().reference.child("cars").addListenerForSingleValueEvent {
			onDataChange {
				val cars = listDeserializer<Car>().apply(it)
				cars.filter {
					if (role == Role.DRIVER) {
						it.drivers.forEach { driver ->
							if (driver.getKeyOrId() == uid) {
								return@filter true
							}
						}
					} else if (role == Role.TEACHER) {
						it.teachers.forEach { teacher ->
							if (teacher.getKeyOrId() == uid) {
								return@filter true
							}
						}
					}
					return@filter false
				}.also {
					if (it.isNotEmpty()) {
						IVan.setCar(applicationContext, it[0])
						onCarSelect()
					}
				}
			}
		}
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

	@RequiresApi(api = Build.VERSION_CODES.M)
	private fun requestOverlayPermission() {
		if (!canDrawOverlays(this)) {
			val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
			startActivityForResult(intent, REQUEST_CODE_ASK_OVERLAY_PERMISSION)
		}
	}

	private fun setUpDrawer() {
		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()

		nav_view.setNavigationItemSelectedListener(this)
	}

	private fun connectCustomTabsService() {
		val chromePackage = "com.android.chrome"
		customTabsConnection = object : CustomTabsServiceConnection() {
			override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) {
				createCustomTabsSessions(client)
			}

			override fun onServiceDisconnected(name: ComponentName?) {
			}
		}
	}

	private fun createCustomTabsSessions(client: CustomTabsClient?) {
		customTabsSession = client?.newSession(object : CustomTabsCallback() {
			override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {

			}
		})
	}

	override fun onDestroy() {
		super.onDestroy()
		customTabsConnection?.let {
			unbindService(it)
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

	@TargetApi(Build.VERSION_CODES.M)
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			REQUEST_CODE_ASK_OVERLAY_PERMISSION -> {
				if (!Settings.canDrawOverlays(this)) {
					requestOverlayPermission()
				} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					if (!canDrawOverlays(this)) {
						requestOverlayPermission()
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private fun canDrawOverlays(context: Context): Boolean {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        return Settings.canDrawOverlays(context)
    } else {
        if (Settings.canDrawOverlays(context)) return true
        try {
            val mgr = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return false
            val viewToAdd = View(context)
            val params = WindowManager.LayoutParams(0, 0, if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT)
			viewToAdd.layoutParams = params
            mgr.addView(viewToAdd, params)
            mgr.removeView(viewToAdd)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_settings -> {
				startActivity<SettingsActivity>()
				true
			}
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
				fragment = SelectCarFragment.newInstance()
				tag = SelectCarFragment.TAG
			}
			R.id.nav_car_tracking, R.id.nav_car_tracking_driver, R.id.nav_car_tracking_teacher -> {
				fragment = CarMapFragment.newInstance()
				tag = CarMapFragment.TAG
			}
			R.id.nav_student_list, R.id.nav_student_list_driver, R.id.nav_student_list_teacher -> {
				fragment = StudentsFragment.newInstance()
				tag = StudentsFragment.TAG
			}
			R.id.nav_driver, R.id.nav_driver_teacher -> {
				startActivity<DriverActivity>(DriverActivity.EXTRA_DRIVER_ID to IVan.getCar(applicationContext).drivers[0].getKeyOrId())
				return
			}
			R.id.nav_profile, R.id.nav_profile_driver, R.id.nav_profile_teacher -> {
				IVan.getUser(applicationContext).fold {
					onParent {
						startActivity<ParentActivity>(ParentActivity.EXTRA_PARENT_ID to it.getKeyOrId())
					}
					onDriver {
						startActivity<DriverActivity>(DriverActivity.EXTRA_DRIVER_ID to it.getKeyOrId())
					}
					onTeacher {
						startActivity<TeacherActivity>(TeacherActivity.EXTRA_TEACHER_ID to it.getKeyOrId())
					}
				}
				return
			}
			R.id.nav_live, R.id.nav_live_driver, R.id.nav_live_teacher -> {
				val uri = Uri.parse("https://media-ivan.meranote.in.th/player/${IVan.getCar(applicationContext).getKeyOrId()}")
				val builder = CustomTabsIntent.Builder(customTabsSession)
				builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
				builder.setShowTitle(true)
				val intent = builder.build()
				intent.launchUrl(this, uri)
				return
			}
			R.id.nav_notification, R.id.nav_notification_driver, R.id.nav_notification_teacher -> {
				fragment = NotificationFragment.newInstance()
				tag = NotificationFragment.TAG
			}
			R.id.nav_logout -> {
				FirebaseAuth.getInstance().signOut()
				IVan.clear(applicationContext)
				startActivity<LoginActivity>()
				finish()
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