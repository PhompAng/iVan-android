package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 14/1/2018 AD.
 */

data class Student(@get:PropertyName("address") @set:PropertyName("address") var address: Address = Address(),
				   @get:PropertyName("location") @set:PropertyName("location") var location: Location = Location(),
				   @get:PropertyName("car") @set:PropertyName("car") var car: String = "",
				   @get:PropertyName("name") @set:PropertyName("name") var name: Name = Name(),
				   @get:PropertyName("no") @set:PropertyName("no") var no: String = "",
				   @get:PropertyName("text") @set:PropertyName("text") var text: String = "",
				   @get:PropertyName("parent") @set:PropertyName("parent") var parent: String = "",
				   @get:PropertyName("school") @set:PropertyName("school") var school: String = "") : FirebaseModel() {
	fun getFullName(): String = this.fullName()
}

fun Student.fullName(): String = "${this.name.thFirst} ${this.name.thLast}"