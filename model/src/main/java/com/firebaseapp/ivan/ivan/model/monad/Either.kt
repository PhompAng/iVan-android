package com.firebaseapp.ivan.ivan.model.monad

/**
 * @author phompang on 11/3/2018 AD.
 */

sealed class Either<out A, out B> {
	data class Left<out A>(val a: A): Either<A, Nothing>()
	data class Right<out B>(val b: B): Either<Nothing, B>()
}

fun <A> left(a: A): Either<A, Nothing> = Either.Left(a)
fun <B> right(b: B): Either<Nothing, B> = Either.Right(b)

fun <A, B> Either<A, B>?.fold(init: FoldHelper<A, B>.() -> Unit) {
	val helper = FoldHelper(this)
	helper.init()
	helper.fold()
}
class FoldHelper<out A, out B>(private val either: Either<A, B>?) {
	private var onLeft: ((A) -> Unit)? = null
	private var onRight: ((B) -> Unit)? = null
	private var onNull: () -> Unit = {}

	fun onLeft(onLeft: (A) -> Unit) {
		this.onLeft = onLeft
	}

	fun onRight(onRight: (B) -> Unit) {
		this.onRight = onRight
	}

	fun onNull(onNull: () -> Unit) {
		this.onNull = onNull
	}

	fun fold() {
		when (either) {
			null -> onNull.invoke()
			is Either.Left -> onLeft?.invoke(either.a)
			is Either.Right -> onRight?.invoke(either.b)
		}
	}
}