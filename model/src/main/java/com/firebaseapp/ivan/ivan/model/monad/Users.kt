package com.firebaseapp.ivan.ivan.model.monad

/**
 * @author phompang on 11/3/2018 AD.
 */

sealed class Users<out A, out B, out C> {
	data class Parent<out A>(val a: A): Users<A, Nothing, Nothing>()
	data class Driver<out B>(val b: B): Users<Nothing, B, Nothing>()
	data class Teacher<out C>(val c: C): Users<Nothing, Nothing, C>()
}

fun <A> parent(a: A): Users<A, Nothing, Nothing> = Users.Parent(a)
fun <B> driver(b: B): Users<Nothing, B, Nothing> = Users.Driver(b)
fun <C> teacher(c: C): Users<Nothing, Nothing, C> = Users.Teacher(c)

fun <A, B, C> Users<A, B, C>?.fold(init: UsersFoldHelper<A, B, C>.() -> Unit) {
	val helper = UsersFoldHelper(this)
	helper.init()
	helper.fold()
}
class UsersFoldHelper<out A, out B, out C>(private val users: Users<A, B, C>?) {
	private var onParent: ((A) -> Unit)? = null
	private var onDriver: ((B) -> Unit)? = null
	private var onTeacher: ((C) -> Unit)? = null
	private var onNull: () -> Unit = {}

	fun onParent(onParent: (A) -> Unit) {
		this.onParent = onParent
	}

	fun onDriver(onDriver: (B) -> Unit) {
		this.onDriver = onDriver
	}

	fun onTeacher(onTeacher: (C) -> Unit) {
		this.onTeacher = onTeacher
	}

	fun onNull(onNull: () -> Unit) {
		this.onNull = onNull
	}

	fun fold() {
		when (users) {
			null -> onNull.invoke()
			is Users.Parent -> onParent?.invoke(users.a)
			is Users.Driver -> onDriver?.invoke(users.b)
			is Users.Teacher -> onTeacher?.invoke(users.c)
		}
	}
}