package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 18/1/2018 AD.
 */
data class Teacher(@get:PropertyName("car") @set:PropertyName("car") var car: String = "",
				   @get:PropertyName("email") @set:PropertyName("email") var email: String = "",
				   @get:PropertyName("name") @set:PropertyName("name") var name: Name = Name(),
				   @get:PropertyName("school") @set:PropertyName("school") var school: String = "",
				   @get:PropertyName("text") @set:PropertyName("text") var text: String = "",
				   @get:PropertyName("telephone") @set:PropertyName("telephone") var telephone: String = "") : FirebaseModel()

fun Teacher.fullName(): String = "${this.name.thFirst} ${this.name.thLast}"