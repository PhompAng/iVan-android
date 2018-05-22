package com.firebaseapp.ivan.util

import com.google.firebase.database.*
import timber.log.Timber

/**
 * @author phompang on 18/1/2018 AD.
 */

fun Query.addListenerForSingleValueEvent(init: ValueEventHelper.() -> Unit) {
	val helper = ValueEventHelper()
	helper.init()
	this.addListenerForSingleValueEvent(helper)
}

fun Query.addValueEventListener(init: ValueEventHelper.() -> Unit) {
	val helper = ValueEventHelper()
	helper.init()
	this.addValueEventListener(helper)
}

private typealias dataListener = (DataSnapshot?) -> Unit
class ValueEventHelper : ValueEventListener {
	private var dataChange: dataListener? = null

	fun onDataChange(dataChange: dataListener) {
		this.dataChange = dataChange
	}

	override fun onCancelled(p0: DatabaseError?) {
		Timber.e(p0?.message)
	}

	override fun onDataChange(p0: DataSnapshot?) {
		dataChange?.invoke(p0)
	}

}