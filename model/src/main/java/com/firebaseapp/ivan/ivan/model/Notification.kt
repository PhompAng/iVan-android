package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 13/2/2018 AD.
 */
data class Notification(
		@get:PropertyName("alarm_status") @set:PropertyName("alarm_status")
		var alarmStatus: AlarmStatus = AlarmStatus(),
		@get:PropertyName("car") @set:PropertyName("car")
		var car: String = "",
		@get:PropertyName("school") @set:PropertyName("school")
		var school: String = "",
		@get:PropertyName("text") @set:PropertyName("text")
		var text: String = "",
		@get:PropertyName("timestamp") @set:PropertyName("timestamp")
		var timestamp: Long = 0
) : FirebaseModel()