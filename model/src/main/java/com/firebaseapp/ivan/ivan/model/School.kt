package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 18/1/2018 AD.
 */

data class School(@get:PropertyName("address") @set:PropertyName("address") var address: Address = Address(),
				  @get:PropertyName("location") @set:PropertyName("location") var location: Location = Location(),
				  @get:PropertyName("name") @set:PropertyName("name") var name: Name = Name(),
				  @get:PropertyName("tel") @set:PropertyName("tel") var tel: String = "") : FirebaseModel()