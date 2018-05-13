package com.firebaseapp.ivan.ivan.service

import android.content.Intent
import android.os.Bundle
import com.firebaseapp.ivan.ivan.helper.NotificationHelper
import com.firebaseapp.ivan.ivan.model.delegate.DelegateAlarmStatus
import com.firebaseapp.ivan.ivan.service.NotificationOverlayService.Companion.EXTRA_ALARM
import com.firebaseapp.ivan.util.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


/**
* @author phompang on 12/13/2017 AD.
*/
class MyFirebaseMessagingService: FirebaseMessagingService() {

	override fun onMessageReceived(remoteMessage: RemoteMessage?) {
		remoteMessage?.notification?.let {
			Timber.d( "${it.title}: ${it.body}")
		}
		remoteMessage?.data?.let {
			if (it.isNotEmpty()) {
				Timber.d(it.toString())
				when (it[PAYLOAD_TYPE]) {
					NOTIFICATION_TYPE_ALERT, NOTIFICATION_TEST -> {
						val title = "ALERT!!"
						val text = "We detect something leftover in car ${it[PAYLOAD_CAR_PLATE_NUMBER]}"
						logAnalytic(it)
						showOverlay(title, text, it)
						sendNotification(title, text)
					}
					NOTIFICATION_TYPE_NOTIFY -> {
						sendNotification("WARNING", "รถใกล้ถึงบ้านแล้ว")
					}
					NOTIFICATION_CONFIRM -> {
						sendNotification("ALERT!!", "เด็กได้รับการช่วยเหลือแล้ว")
					}
				}
			}
		}
	}

	private fun showOverlay(title: String, text: String, data: Map<String, String>) {
		//TODO job dispatcher
		val alarm = DelegateAlarmStatus(
				title,
				text,
				data[PAYLOAD_CAR_ID]!!,
				data[PAYLOAD_LAT]?.toDouble()!!,
				data[PAYLOAD_LNG]?.toDouble()!!,
				data[PAYLOAD_UID]!!)
		val intent = Intent(applicationContext, NotificationOverlayService::class.java)
		intent.putExtra(EXTRA_ALARM, alarm)
		startService(intent)
	}

	private fun logAnalytic(data: Map<String, String>) {
		val analytic = FirebaseAnalytics.getInstance(this)
		val bundle = Bundle().apply {
			putString(PARAM_CAR_ID, data[PAYLOAD_CAR_ID])
			putString(PARAM_CAR_PLATE_NUMBER, data[PAYLOAD_CAR_PLATE_NUMBER])
			putString(PARAM_LAT_LNG, "${data[PAYLOAD_LAT]}_${data[PAYLOAD_LNG]}")
			putString(PARAM_SCHOOL_ID, data[PAYLOAD_SCHOOL_ID])
		}
		analytic.logEvent(EVENT_ALERT, bundle)
	}

	private fun sendNotification(title: String?, body: String?) {
		//TODO notification type
		if (title != null && body != null) {
			val helper = NotificationHelper(this)
			val noti = helper.getDetectNotification(title, body)
			helper.notify(1000, noti)
		}
	}
}
