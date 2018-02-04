package com.firebaseapp.ivan.ivan.model

import android.arch.core.util.Function
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.PropertyName

/**
 * @author phompang on 18/1/2018 AD.
 */

abstract class FirebaseModel {
	@get:PropertyName("key") @set:PropertyName("key") var key = ""
	@get:PropertyName("id") @set:PropertyName("id") var id = ""

	fun getKeyOrId(): String = when {
		this.key.isNotBlank() -> this.key
		this.id.isNotBlank() -> this.id
		else -> ""
	}
}

inline fun <reified T : FirebaseModel> deserializer(): Function<DataSnapshot, T> {
	return Function { input ->
		val data = input.getValue(T::class.java)
		data?.key = input.key
		data
	}
}

inline fun <reified T : FirebaseModel> listDeserializer(): Function<DataSnapshot, List<T>> {
	return Function { input ->
		val result = mutableListOf<T>()
		input.children.mapNotNullTo(result) {
			val data = it.getValue<T>(T::class.java)
			data?.key = it.key
			data
		}
		result
	}
}