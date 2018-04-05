package com.firebaseapp.ivan.ivan.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.*
import com.firebaseapp.ivan.ivan.model.monad.Users
import com.firebaseapp.ivan.ivan.model.monad.left
import com.firebaseapp.ivan.ivan.model.monad.right
import com.firebaseapp.ivan.util.either
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 21/1/2018 AD.
 */
class MainViewModel : ViewModel() {
	private val userRef = FirebaseDatabase.getInstance().reference.child("users")
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")
	private val driverRef = FirebaseDatabase.getInstance().reference.child("drivers")
	private var userUid = MutableLiveData<String>()
	private var user = userUid.switchMap {
		FirebaseLiveData(userRef.child(it), deserializer<User>()).getLiveData()
	}

	fun setUid(uid: String) {
		this.userUid.value = uid
	}

	fun getUser(): LiveData<Users<Parent, Driver>> {
		return user.either {
			when (it.role) {
				Role.PARENT -> {
					left(
							FirebaseLiveData(
									parentRef.child(it.getKeyOrId()),
									deserializer<Parent>()
							).getLiveData()
					)
				}
				Role.DRIVER -> {
						right(
								FirebaseLiveData(
										driverRef.child(it.getKeyOrId()),
										deserializer<Driver>()
								).getLiveData()
						)
				}
				else -> throw IllegalArgumentException("aaa")
			}
		}
	}
}