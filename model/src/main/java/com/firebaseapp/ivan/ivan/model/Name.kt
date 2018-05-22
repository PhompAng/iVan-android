package com.firebaseapp.ivan.ivan.model

import com.google.firebase.database.PropertyName

/**
 * @author phompang on 14/1/2018 AD.
 */
data class Name(@get:PropertyName("en_first") @set:PropertyName("en_first") var enFirst: String = "",
				@get:PropertyName("en_last") @set:PropertyName("en_last") var enLast: String = "",
				@get:PropertyName("th_first") @set:PropertyName("th_first") var thFirst: String = "",
				@get:PropertyName("th_last") @set:PropertyName("th_last") var thLast: String = "",
				@get:PropertyName("th") @set:PropertyName("th") var th: String = "",
				@get:PropertyName("en") @set:PropertyName("en") var en: String = ""): FirebaseModel()