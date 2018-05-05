package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 14/1/2018 AD.
 */

data class Student1(@get:PropertyName("address")
				   @set:PropertyName("address")
				   var address: Address = Address(),
				   @get:PropertyName("location")
				   @set:PropertyName("location")
				   var location: Location = Location(),
				   @get:PropertyName("car")
				   @set:PropertyName("car")
				   var car: String = "",
				   @get:PropertyName("name")
				   @set:PropertyName("name")
				   var name: Name = Name(),
				   @get:PropertyName("no")
				   @set:PropertyName("no")
				   var no: String = "",
				   @get:PropertyName("text")
				   @set:PropertyName("text")
				   var text: String = "",
				   @get:PropertyName("parent")
				   @set:PropertyName("parent")
				   var parent: String = "",
				   @get:PropertyName("school")
				   @set:PropertyName("school")
				   var school: String = "",
				   @get:PropertyName("car_history")
				   @set:PropertyName("car_history")
				   var carHistory: Map<String, Map<String, Student>> = HashMap<String, Map<String, Student>>(),
				   @get:PropertyName("timestamp")
				   @set:PropertyName("timestamp")
				   var timestamp: Long = 0) : FirebaseModel() {
}