package com.firebaseapp.ivan.util.livedata

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*
import timber.log.Timber
import android.arch.core.util.Function
import android.arch.lifecycle.Transformations

/**
 * @author phompang on 14/1/2018 AD.
 */
class FirebaseLiveData<T> : LiveData<DataSnapshot> {

	private val query: Query
	private val listener = MyValueEventListener()
	private val deserializer: Function<DataSnapshot, T>

	constructor(query: Query, deserializer: Function<DataSnapshot, T>) {
		this.query = query
		this.deserializer = deserializer
		Timber.d(query.ref.toString())
	}

	constructor(ref: DatabaseReference, deserializer: Function<DataSnapshot, T>) {
		this.query = ref
		this.deserializer = deserializer
		Timber.d(query.ref.toString())
	}

	override fun onActive() {
		query.addValueEventListener(listener)
	}

	override fun onInactive() {
		query.removeEventListener(listener)
	}

	fun getLiveData(): LiveData<T> {
		return Transformations.map(this, this.deserializer)
	}

	inner class MyValueEventListener : ValueEventListener {
		override fun onCancelled(databaseError: DatabaseError?) {
			Timber.e("Can't listen to query $query${databaseError?.toException()}")
		}

		override fun onDataChange(dataSnapshot: DataSnapshot?) {
			value = dataSnapshot
		}
	}
}