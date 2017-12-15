package com.firebaseapp.ivan.ivan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebaseapp.ivan.ivan.choose.ChooseCarActivity
import com.firebaseapp.ivan.ivan.helper.NotificationHelper
import com.firebaseapp.ivan.ivan.utils.toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

	private lateinit var mAuth: FirebaseAuth
	private lateinit var notificationHelper: NotificationHelper

	companion object {
		private val TAG = LoginActivity::class.java.simpleName
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		mAuth = FirebaseAuth.getInstance()
		notificationHelper = NotificationHelper(this)
		signInButton.setOnClickListener {
			signIn()
		}
	}

	override fun onStart() {
		super.onStart()
		val currentUser = mAuth.currentUser
		currentUser?.let {
			//TODO Skip Login
		}
	}

	private fun signIn() {
		val email = emailEditText.text.toString()
		val password = passwordEditText.text.toString()
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this) { task: Task<AuthResult> ->
					when (task.isSuccessful) {
						true -> {
							startActivity(Intent(baseContext, ChooseCarActivity::class.java))
						}
						else -> {
							Log.e(TAG, "signInWithEmailAndPassword" + task.exception)
							toast("signInWithEmailAndPassword" + task.exception)
						}
					}
				}
	}
}
