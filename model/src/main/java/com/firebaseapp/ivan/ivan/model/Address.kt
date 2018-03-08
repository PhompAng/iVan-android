package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 14/1/2018 AD.
 */
data class Address(@get:PropertyName("city") @set:PropertyName("city") var city: String = "",
				   @get:PropertyName("district") @set:PropertyName("district") var district: String = "",
				   @get:PropertyName("line1") @set:PropertyName("line1") var line1: String = "",
				   @get:PropertyName("line2") @set:PropertyName("line2") var line2: String = "",
				   @get:PropertyName("postcode") @set:PropertyName("postcode") var postcode: Int = 0,
				   @get:PropertyName("province") @set:PropertyName("province") var province: String = "") : FirebaseModel()

fun Address.simpleAddress() = "$line1 $line2 \n$district $city $province"