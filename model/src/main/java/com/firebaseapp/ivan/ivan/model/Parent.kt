package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 21/1/2018 AD.
 */
data class Parent(@get:PropertyName("address") @set:PropertyName("address") var address: Address = Address(),
				   @get:PropertyName("location") @set:PropertyName("location") var location: Location = Location(),
				   @get:PropertyName("email") @set:PropertyName("email") var email: String = "",
				   @get:PropertyName("telephone") @set:PropertyName("telephone") var telephone: String = "",
				   @get:PropertyName("name") @set:PropertyName("name") var name: Name = Name(),
				   @get:PropertyName("school") @set:PropertyName("school") var school: String = "") : FirebaseModel()

fun Parent.fullName(): String = "${this.name.thFirst} ${this.name.thLast}"