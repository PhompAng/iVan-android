package com.firebaseapp.ivan.util

import android.content.Context
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Parent

/**
 * @author phompang on 21/1/2018 AD.
 */

object IVan {
	fun setUser(context: Context, parent: Parent) {
		val krefson = Krefson(context)
		krefson[Krefson.KEY_PARENT] = parent
	}

	fun getUser(context: Context): Parent {
		val krefson = Krefson(context)
		return krefson[Krefson.KEY_PARENT, Parent()]
	}

	fun setCar(context: Context, car: Car) {
		val krefson = Krefson(context)
		krefson[Krefson.KEY_CAR] = car
	}

	fun getCar(context: Context): Car {
		val krefson = Krefson(context)
		return krefson[Krefson.KEY_CAR, Car()]
	}
}