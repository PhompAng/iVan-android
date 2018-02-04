package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 14/1/2018 AD.
 */
data class Location(@get:PropertyName("lat") @set:PropertyName("lat") var lat: Double = 0.0,
					@get:PropertyName("lng") @set:PropertyName("lng") var lng: Double = 0.0) : FirebaseModel()