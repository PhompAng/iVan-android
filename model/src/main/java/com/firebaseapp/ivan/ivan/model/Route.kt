package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 27/3/2018 AD.
 */
data class Route(
		@get:PropertyName("routes") @set:PropertyName("routes")
		var routes: String = "",
		@get:PropertyName("waypoints") @set:PropertyName("waypoints")
		var waypoints: List<Student> = mutableListOf()
) : FirebaseModel()