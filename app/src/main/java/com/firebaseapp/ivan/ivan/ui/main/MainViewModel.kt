package com.firebaseapp.ivan.ivan.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.*
import com.firebaseapp.ivan.ivan.model.monad.Users
import com.firebaseapp.ivan.ivan.model.monad.driver
import com.firebaseapp.ivan.ivan.model.monad.parent
import com.firebaseapp.ivan.ivan.model.monad.teacher
import com.firebaseapp.ivan.util.either
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * @author phompang on 21/1/2018 AD.
 */
class MainViewModel @Inject constructor() : ViewModel() {
	private val userRef = FirebaseDatabase.getInstance().reference.child("users")
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")
	private val driverRef = FirebaseDatabase.getInstance().reference.child("drivers")
	private val teacherRef = FirebaseDatabase.getInstance().reference.child("teachers")
	private var userUid = MutableLiveData<String>()
	private var user = userUid.switchMap {
		FirebaseLiveData(userRef.child(it), deserializer<User>()).getLiveData()
	}

	fun setUid(uid: String) {
		this.userUid.value = uid
	}

	fun getUser(): LiveData<Users<Parent, Driver, Teacher>> {
		return user.either {
			when (it.role) {
				Role.PARENT -> {
					parent(
							FirebaseLiveData(
									parentRef.child(it.getKeyOrId()),
									deserializer<Parent>()
							).getLiveData()
					)
				}
				Role.DRIVER -> {
						driver(
								FirebaseLiveData(
										driverRef.child(it.getKeyOrId()),
										deserializer<Driver>()
								).getLiveData()
						)
				}
				Role.TEACHER -> {
					teacher(
							FirebaseLiveData(
									teacherRef.child(it.getKeyOrId()),
									deserializer<Teacher>()
							).getLiveData()
					)
				}
				else -> throw IllegalArgumentException("aaa")
			}
		}
	}
}