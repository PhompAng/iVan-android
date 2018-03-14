package com.firebaseapp.ivan.util

import android.content.Context
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Driver
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.monad.Either
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.model.monad.left
import com.firebaseapp.ivan.ivan.model.monad.right

/**
 * @author phompang on 21/1/2018 AD.
 */

object IVan {
	fun setUser(context: Context, user: Either<Parent, Driver>) {
		val krefson = Krefson(context)
		user.fold {
			onLeft {
				krefson[Krefson.KEY_PARENT] = it
			}
			onRight {
				krefson[Krefson.KEY_DRIVER] = it
			}
		}
	}

	fun getUser(context: Context): Either<Parent, Driver>? {
		val krefson = Krefson(context)
		val parent: Parent? = krefson[Krefson.KEY_PARENT]

		if (parent != null) {
			return left(parent)
		} else {
			val driver: Driver? = krefson[Krefson.KEY_DRIVER]
			driver?.let {
				return right(it)
			}
		}
		return null
	}

	fun getParent(context: Context): Parent? {
		var parent: Parent? = null
		getUser(context).fold {
			onLeft { parent = it }
		}
		return parent
	}

	fun getDriver(context: Context): Driver? {
		var driver: Driver? = null
		getUser(context).fold {
			onRight { driver = it }
		}
		return driver
	}

	fun setCar(context: Context, car: Car) {
		val krefson = Krefson(context)
		krefson[Krefson.KEY_CAR] = car
	}

	fun getCar(context: Context): Car {
		val krefson = Krefson(context)
		return krefson[Krefson.KEY_CAR, Car()]
	}

	fun getCarNullable(context: Context): Car? {
		val krefson = Krefson(context)
		return krefson[Krefson.KEY_CAR]
	}

	fun clear(context: Context) {
		val krefson = Krefson(context)
		krefson.clear()
	}
}