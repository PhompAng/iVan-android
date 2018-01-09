package com.firebaseapp.ivan.ivan.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import com.firebaseapp.ivan.ivan.R

/**
 * Created by phompang on 12/13/2017 AD.
 */

internal class NotificationHelper(ctx: Context) : ContextWrapper(ctx) {
	private val manager: NotificationManager by lazy {
		getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
	}
	val defaultChannelId: String by lazy { getString(R.string.default_notification_channel_id) }
	private val defaultChannelName by lazy { getString(R.string.default_notification_channel_name) }
	val detectChannelId: String by lazy { getString(R.string.detect_notification_channel_id) }
	private val detectChannelName by lazy { getString(R.string.detect_notification_channel_name) }
	private val alertGroupId by lazy { getString(R.string.alert_notification_group_id) }
	private val alertGroupName by lazy { getString(R.string.alert_notification_group_name) }

	init {
		val defaultChannel = NotificationChannel(defaultChannelId, defaultChannelName, NotificationManager.IMPORTANCE_DEFAULT)
		defaultChannel.lightColor = Color.GREEN
		manager.createNotificationChannel(defaultChannel)

		val alertGroup = NotificationChannelGroup(alertGroupId, alertGroupName)
		manager.createNotificationChannelGroup(alertGroup)

		val detectChannel = NotificationChannel(detectChannelId, detectChannelName, NotificationManager.IMPORTANCE_HIGH)
		defaultChannel.lightColor = Color.RED
		defaultChannel.group = alertGroupId
		manager.createNotificationChannel(detectChannel)
	}

	fun getDetectNotification(title: String, body: String): Notification.Builder {
		return Notification.Builder(applicationContext, detectChannelId)
				.setContentTitle(title)
				.setContentText(body)
				.setSmallIcon(R.drawable.ic_error_outline_black_24dp)
	}

	fun notify(id: Int, notification: Notification.Builder) {
		manager.notify(id, notification.build())
	}
}
