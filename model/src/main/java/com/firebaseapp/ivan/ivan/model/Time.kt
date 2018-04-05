package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName
import java.util.*

/**
 * @author phompang on 18/1/2018 AD.
 */
data class Time(@get:PropertyName("evening") @set:PropertyName("evening") var evening: WorkingHour = WorkingHour(),
				@get:PropertyName("morning") @set:PropertyName("morning") var morning: WorkingHour = WorkingHour()) : FirebaseModel()

data class WorkingHour(@get:PropertyName("end") @set:PropertyName("end") var end: Hour = Hour(),
					   @get:PropertyName("start") @set:PropertyName("start") var start: Hour = Hour()) : FirebaseModel()

data class Hour(@get:PropertyName("HH") @set:PropertyName("HH") var hh: String = "",
				@get:PropertyName("mm") @set:PropertyName("mm") var mm: String = "") : FirebaseModel()

fun WorkingHour.containNow(): Boolean {
	val startTime = Calendar.getInstance()
	startTime.set(Calendar.HOUR, this.start.hh.toInt())
	startTime.set(Calendar.MINUTE, this.start.mm.toInt())

	val endTime = Calendar.getInstance()
	endTime.set(Calendar.HOUR, this.end.hh.toInt())
	endTime.set(Calendar.MINUTE, this.end.mm.toInt())

	val now = Calendar.getInstance()
	return now.after(startTime) && now.before(endTime)
}