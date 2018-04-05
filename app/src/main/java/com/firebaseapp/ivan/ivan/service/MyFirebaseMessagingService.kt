package com.firebaseapp.ivan.ivan.service

import android.os.Bundle
import com.firebaseapp.ivan.ivan.helper.NotificationHelper
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
			sendNotification(it.title, it.body)
		}
		remoteMessage?.data?.let {
			if (it.isNotEmpty()) {
				Timber.d(it.toString())
				when (it[PAYLOAD_TYPE]) {
					NOTIFICATION_TYPE_ALERT -> logAnalytic(it)
				}
			}
		}
	}

	private fun logAnalytic(data: Map<String, String>) {
		val analytic = FirebaseAnalytics.getInstance(this)
		val bundle = Bundle().apply {
			putString(PARAM_CAR_ID, data[PAYLOAD_CAR_ID])
			putString(PARAM_SCHOOL_ID, data[PAYLOAD_SCHOOL_ID])
		}
		analytic.logEvent(EVENT_ALERT, bundle)
	}

	private fun sendNotification(title: String?, body: String?) {
		if (title != null && body != null) {
			val helper = NotificationHelper(this)
			val noti = helper.getDetectNotification(title, body)
			helper.notify(1000, noti)
		}
	}
}
