package com.firebaseapp.ivan.util

import android.arch.lifecycle.*
import com.firebaseapp.ivan.ivan.model.monad.Users
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.model.monad.left
import com.firebaseapp.ivan.ivan.model.monad.right

/**
 * @author phompang on 9/1/2018 AD.
 */

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer<T> { t -> observer.invoke(t) })

fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
	return MediatorLiveData<Pair<A, B>>().apply {
		var lastA: A? = null
		var lastB: B? = null

		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			if (localLastA != null && localLastB != null)
				this.value = Pair(localLastA, localLastB)
		}

		addSource(a) {
			lastA = it
			update()
		}
		addSource(b) {
			lastB = it
			update()
		}
	}
}

fun <A, B> LiveData<A>.zip(b: LiveData<B>): LiveData<Pair<A, B>> = zipLiveData(this, b)

fun <A, B> LiveData<A>.map(function: (A) -> B): LiveData<B> = Transformations.map(this, function)

fun <A, B> LiveData<A>.switchMap(function: (A) -> LiveData<B>): LiveData<B> = Transformations.switchMap(this, function)

fun <T> LiveData<List<T>>.filter(condition: (T) -> Boolean): LiveData<List<T>> {
	val result = MediatorLiveData<List<T>>()
	result.addSource(this) { t: List<T>? ->
		val resultList = mutableListOf<T>()
		t?.let { list ->
			list.forEach {
				if (condition(it)) {
					resultList += it
				}
			}
		}
		result.value = resultList
	}
	return result
}

fun <A, B, C> LiveData<C>.either(condition: (C) -> Users<LiveData<A>, LiveData<B>>): LiveData<Users<A, B>> {
	return MediatorLiveData<Users<A, B>>().apply {
		var e: Users<LiveData<A>, LiveData<B>>? = null

		fun update() {
			e.fold {
				onParent {
					addSource(it) { a ->
						a?.let {
							this@apply.value = left(it)
						}
					}
				}
				onDriver {
					addSource(it) { b ->
						b?.let {
							this@apply.value = right(it)
						}
					}
				}
			}
		}

		addSource(this@either) { c: C? ->
			c?.let {
				e = condition(it)
				update()
			}
		}
	}
}