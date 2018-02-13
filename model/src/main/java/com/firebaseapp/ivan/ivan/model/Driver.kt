package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 18/1/2018 AD.
 */
data class Driver(@get:PropertyName("address") @set:PropertyName("address") var address: Address = Address(),
				  @get:PropertyName("alarm_status") @set:PropertyName("alarm_status") var alarmStatus: Map<String, AlarmStatus> = mutableMapOf(),
				  @get:PropertyName("car") @set:PropertyName("car") var car: String = "",
				  @get:PropertyName("email") @set:PropertyName("email") var email: String = "",
				  @get:PropertyName("location") @set:PropertyName("location") var location: Location = Location(),
				  @get:PropertyName("name") @set:PropertyName("name") var name: Name = Name(),
				  @get:PropertyName("school") @set:PropertyName("school") var school: String = "",
				  @get:PropertyName("telephone") @set:PropertyName("telephone") var telephone: String = "",
				  @get:PropertyName("text") @set:PropertyName("text") var text: String = "") : FirebaseModel()

fun Driver.fullName(): String = "${this.name.thFirst} ${this.name.thLast}"