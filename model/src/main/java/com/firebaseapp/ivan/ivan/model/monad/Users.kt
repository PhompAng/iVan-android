package com.firebaseapp.ivan.ivan.model.monad

/**
 * @author phompang on 11/3/2018 AD.
 */

sealed class Users<out A, out B> {
	data class Parent<out A>(val a: A): Users<A, Nothing>()
	data class Driver<out B>(val b: B): Users<Nothing, B>()
}

fun <A> left(a: A): Users<A, Nothing> = Users.Parent(a)
fun <B> right(b: B): Users<Nothing, B> = Users.Driver(b)

fun <A, B> Users<A, B>?.fold(init: FoldHelper<A, B>.() -> Unit) {
	val helper = FoldHelper(this)
	helper.init()
	helper.fold()
}
class FoldHelper<out A, out B>(private val users: Users<A, B>?) {
	private var onParent: ((A) -> Unit)? = null
	private var onDriver: ((B) -> Unit)? = null
	private var onNull: () -> Unit = {}

	fun onParent(onParent: (A) -> Unit) {
		this.onParent = onParent
	}

	fun onDriver(onDriver: (B) -> Unit) {
		this.onDriver = onDriver
	}

	fun onNull(onNull: () -> Unit) {
		this.onNull = onNull
	}

	fun fold() {
		when (users) {
			null -> onNull.invoke()
			is Users.Parent -> onParent?.invoke(users.a)
			is Users.Driver -> onDriver?.invoke(users.b)
		}
	}
}