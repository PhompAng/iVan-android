package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 23/1/2018 AD.
 */


data class MobilityStatus(@get:PropertyName("timestamp")
						  @set:PropertyName("timestamp")
						  var timestamp: Long = 0,
						  @get:PropertyName("lat")
						  @set:PropertyName("lat")
						  var lat: Double = 0.0,
						  @get:PropertyName("lng")
						  @set:PropertyName("lng")
						  var lng: Double = 0.0,
						  @get:PropertyName("speed")
						  @set:PropertyName("speed")
						  var speed: Double = 0.0,
						  @get:PropertyName("oil_level")
						  @set:PropertyName("oil_level")
						  var oilLevel: Double = 0.0,
						  @get:PropertyName("brake_oil_mileage")
						  @set:PropertyName("brake_oil_mileage")
						  var brakeOilMileage: Long = 0,
						  @get:PropertyName("engine_oil_mileage")
						  @set:PropertyName("engine_oil_mileage")
						  var engineOilMileage: Long = 0,
						  @get:PropertyName("mileage")
						  @set:PropertyName("mileage")
						  var mileage: Long = 0,
						  @get:PropertyName("speed_exceed")
						  @set:PropertyName("speed_exceed")
						  var speedExceed: Int = 0,
						  @get:PropertyName("star")
						  @set:PropertyName("star")
						  var star: Double = 0.0) : FirebaseModel()