package com.firebaseapp.ivan.ivan.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import com.firebaseapp.ivan.ivan.model.Location
import com.firebaseapp.ivan.ivan.model.delegate.DelegateAlarmStatus
import com.firebaseapp.ivan.ivan.ui.alarmstatus.AlarmStatusActivity
import com.firebaseapp.ivan.ivan.ui.map.MapActivity
import com.firebaseapp.ivan.util.view.DetectedOverlay
import org.jetbrains.anko.startActivity
import timber.log.Timber

/**
 * @author phompang on 10/4/2018 AD.
 */
class NotificationOverlayService : Service() {
	private lateinit var windowManager: WindowManager
	private var overlay: DetectedOverlay? = null
	private var data: DelegateAlarmStatus? = null

	companion object {
		const val EXTRA_ALARM = "extra-alarm"
	}

	override fun onBind(intent: Intent?): IBinder? {
		return null
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		data = intent?.extras?.get(EXTRA_ALARM) as DelegateAlarmStatus?
		overlay?.fillData(data)
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onCreate() {
		super.onCreate()
		Timber.d("start")
		windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
		overlay = DetectedOverlay(this)
		overlay?.onOverlayClickListener = OnOverlayClickListener()

		val params = WindowManager.LayoutParams(
				WRAP_CONTENT,
				WRAP_CONTENT,
				TYPE_APPLICATION_OVERLAY,
				FLAG_LAYOUT_IN_SCREEN or FLAG_NOT_FOCUSABLE or FLAG_DIM_BEHIND,
				PixelFormat.TRANSLUCENT)
		params.x = 0
		params.y = 100
		windowManager.addView(overlay, params)
	}

	override fun onDestroy() {
		super.onDestroy()
		overlay?.let {
			windowManager.removeView(it)
		}
	}

	private inner class OnOverlayClickListener : DetectedOverlay.OnOverlayClickListener {
		override fun onNegativeClick() {
			stopSelf()
		}

		override fun onDetailClick() {
			data?.let {
				startActivity<AlarmStatusActivity>(AlarmStatusActivity.EXTRA_UID to it.uid)
			}
			stopSelf()
		}

		override fun onOpenMapClick() {
			data?.let {
				startActivity<MapActivity>(MapActivity.EXTRA_LOCATION to Location(it.lat, it.lng))
			}
			stopSelf()
		}

	}
}