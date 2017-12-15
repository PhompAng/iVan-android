package com.firebaseapp.ivan.ivan.services

import android.media.AudioAttributes
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.media.RingtoneManager
import com.firebaseapp.ivan.ivan.helper.NotificationHelper


/**
 * Created by phompang on 12/13/2017 AD.
 */
class MyFirebaseMessagingService: FirebaseMessagingService() {

	companion object {
		private val TAG = MyFirebaseMessagingService::class.java.simpleName
	}

	override fun onMessageReceived(remoteMessage: RemoteMessage?) {
		remoteMessage?.notification?.let {
			Log.d(TAG, "onMessageReceived" + it.body)
			sendNotification(it.title, it.body)
		}
	}

	private fun sendNotification(title: String?, body: String?) {
		if (title != null && body != null) {
			val helper = NotificationHelper(this)
			val noti = helper.getDetectNotification(title, body)
			helper.notify(1000, noti)
		}
	}
}
