package com.firebaseapp.ivan.ivan.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

/**
 * @author phompang on 9/1/2018 AD.
 */
class LoginViewModel : ViewModel(), FirebaseAuth.AuthStateListener {

	private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
	private val result: MutableLiveData<Task<AuthResult>> = MutableLiveData()

	init {
		mAuth.addAuthStateListener(this)
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
}