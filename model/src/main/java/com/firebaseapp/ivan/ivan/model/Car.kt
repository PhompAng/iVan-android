package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 18/1/2018 AD.
 */
data class Car(@get:PropertyName("chassis") @set:PropertyName("chassis") var chassis: String = "",
			   @get:PropertyName("mobility_status") @set:PropertyName("mobility_status") var mobilityStatus: Map<String, MobilityStatus> = mutableMapOf(),
			   @get:PropertyName("model") @set:PropertyName("model") var model: String = "",
			   @get:PropertyName("plate_number") @set:PropertyName("plate_number") var plateNumber: String = "",
			   @get:PropertyName("school") @set:PropertyName("school") var school: String = "",
			   @get:PropertyName("province") @set:PropertyName("province") var province: String = "",
			   @get:PropertyName("students") @set:PropertyName("students") var students: List<Student> = mutableListOf(),
			   @get:PropertyName("teachers") @set:PropertyName("teachers") var teachers: List<Teacher> = mutableListOf(),
			   @get:PropertyName("drivers") @set:PropertyName("drivers") var drivers: List<Driver> = mutableListOf(),
			   @get:PropertyName("text") @set:PropertyName("text") var text: String = "",
			   @get:PropertyName("time") @set:PropertyName("time") var time: Time = Time()) : FirebaseModel()