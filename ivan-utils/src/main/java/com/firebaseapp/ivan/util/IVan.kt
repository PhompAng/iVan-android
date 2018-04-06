package com.firebaseapp.ivan.util

import android.content.Context
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Driver
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.ivan.model.monad.Users
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.model.monad.parent
import com.firebaseapp.ivan.ivan.model.monad.driver
import com.firebaseapp.ivan.ivan.model.monad.teacher

/**
 * @author phompang on 21/1/2018 AD.
 */

object IVan {
	fun setUser(context: Context, user: Users<Parent, Driver, Teacher>) {
		val krefson = Krefson(context)
		user.fold {
			onParent {
				krefson[Krefson.KEY_PARENT] = it
			}
			onDriver {
				krefson[Krefson.KEY_DRIVER] = it
			}
			onTeacher {
				krefson[Krefson.KEY_TEACHER] = it
			}
		}
	}

	fun getUser(context: Context): Users<Parent, Driver, Teacher>? {
		val krefson = Krefson(context)
		val parent: Parent? = krefson[Krefson.KEY_PARENT]
		val driver: Driver? = krefson[Krefson.KEY_DRIVER]
		val teacher: Teacher? = krefson[Krefson.KEY_TEACHER]

		return when {
			parent != null -> parent(parent)
			driver != null -> driver(driver)
			teacher != null -> teacher(teacher)
			else -> null
		}
	}

	fun getParent(context: Context): Parent? {
		var parent: Parent? = null
		getUser(context).fold {
			onParent { parent = it }
		}
		return parent
	}

	fun getDriver(context: Context): Driver? {
		var driver: Driver? = null
		getUser(context).fold {
			onDriver { driver = it }
		}
		return driver
	}

	fun getTeacher(context: Context): Teacher? {
		var teacher: Teacher? = null
		getUser(context).fold {
			onTeacher { teacher = it}
		}
		return teacher
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