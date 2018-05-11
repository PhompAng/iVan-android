package com.firebaseapp.ivan.ivan.ui.login

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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

/**
 * @author phompang on 9/1/2018 AD.
 */
class LoginViewModel : ViewModel(), FirebaseAuth.AuthStateListener {
	private val userRef = FirebaseDatabase.getInstance().reference.child("users")
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")
	private val driverRef = FirebaseDatabase.getInstance().reference.child("drivers")
	private val teacherRef = FirebaseDatabase.getInstance().reference.child("teachers")
	private var userUid = MutableLiveData<String>()
	private var user = userUid.switchMap {
		FirebaseLiveData(userRef.child(it), deserializer<User>()).getLiveData()
	}

	private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
	private val result: MutableLiveData<Task<AuthResult>> = MutableLiveData()

	init {
		mAuth.addAuthStateListener(this)
	}

	fun setUid(uid: String) {
		this.userUid.value = uid
	}

	fun signIn(email: String, password: String): MutableLiveData<Task<AuthResult>> {
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener { task: Task<AuthResult> ->
					result.postValue(task)
				}
		return result
	}

	override fun onAuthStateChanged(p0: FirebaseAuth) {
		if (mAuth.currentUser == null) {
			Timber.d("null")
			result.postValue(null)
		} else {
			Timber.d(mAuth.currentUser.toString())
		}
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