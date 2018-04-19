package com.firebaseapp.ivan.ivan.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

/**
 * @author phompang on 18/1/2018 AD.
 */
@Parcelize
data class AlarmStatus(@get:PropertyName("data")
					   @set:PropertyName("data")
					   var data: List<AlarmStatusData> = mutableListOf(),
					   @get:PropertyName("carId")
					   @set:PropertyName("carId")
					   var carId: String = "",
					   @get:PropertyName("location")
					   @set:PropertyName("location")
					   var location: Location = Location(),
					   @get:PropertyName("detection")
					   @set:PropertyName("detection")
					   var detection: String = "",
					   @get:PropertyName("timestamp")
					   @set:PropertyName("timestamp")
					   var timestamp: Long = 0,
					   @get:PropertyName("isReportFalse")
					   @set:PropertyName("isReportFalse")
					   var isReportFalse: Boolean = false,
					   @get:PropertyName("uid")
					   @set:PropertyName("uid")
					   var uid: String = "") : FirebaseModel(), Parcelable

@Parcelize
data class AlarmStatusData(@get:PropertyName("data")
						   @set:PropertyName("data")
						   var data: SensorData = SensorData(),
						   @get:PropertyName("detection")
						   @set:PropertyName("detection")
						   var detection: String = "",
						   @get:PropertyName("row")
						   @set:PropertyName("row")
						   var row: Int = 0,
						   @get:PropertyName("sensor_module_id")
						   @set:PropertyName("sensor_module_id")
						   var sensorModuleId: String = "",
						   @get:PropertyName("timestamp")
						   @set:PropertyName("timestamp")
						   var timestamp: Long = 0) : FirebaseModel(), Parcelable

@Parcelize
data class SensorData(@get:PropertyName("pir")
					  @set:PropertyName("pir")
					  var pir: Boolean = true,
					  @get:PropertyName("ultrasonic")
					  @set:PropertyName("ultrasonic")
					  var ultrasonic: Double = 0.0,
					  @get:PropertyName("ultrasonic_based_value")
					  @set:PropertyName("ultrasonic_based_value")
					  var ultrasonicBasedValue: Int = 0) : FirebaseModel(), Parcelable