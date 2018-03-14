package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 11/3/2018 AD.
 */

data class User(
		@get:PropertyName("role") @set:PropertyName("role")
		var role: Int = 0,
		@get:PropertyName("school") @set:PropertyName("school")
		var school: String = "") : FirebaseModel()