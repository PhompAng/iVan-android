package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 3/4/2018 AD.
 */
data class RouteTime(
		@get:PropertyName("evening") @set:PropertyName("evening")
		var evening: Route = Route(),
		@get:PropertyName("morning") @set:PropertyName("morning")
		var morning: Route = Route()
) : FirebaseModel()