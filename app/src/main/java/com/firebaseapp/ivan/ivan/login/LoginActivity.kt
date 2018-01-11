package com.firebaseapp.ivan.ivan.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.ivan.ivan.MainActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.utils.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

	private lateinit var viewModel: LoginViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

		signInButton.setOnClickListener {
			showProgress()
			signIn()
		}
	}

	private fun signIn() {
		val email = emailEditText.text.toString()
		val password = passwordEditText.text.toString()
		viewModel.signIn(email, password).observe(this) { t: Task<AuthResult>? ->
			t ?: return@observe
			when (t.isSuccessful) {
				true -> {
					Timber.d("success")
					FirebaseAuth.getInstance().currentUser?.uid?.let { uid: String ->
						startActivity(MainActivity.createIntent(applicationContext, uid))
						finish()
					}
				}
				else -> {
					Timber.e(t.exception)
					toast("signIn ${t.exception}")
					hideProgress()
				}
			}
		}
	}

	private fun showProgress() {
		signInProgress.show()
		signInButton.invisible()
	}

	private fun hideProgress() {
		signInProgress.invisible()
		signInButton.show()
	}
}
