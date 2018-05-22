package com.firebaseapp.ivan.ivan.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.ui.main.MainActivity
import com.firebaseapp.ivan.util.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : LocalizationActivity() {

	private lateinit var viewModel: LoginViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

		val user = FirebaseAuth.getInstance().currentUser
		if (user != null && IVan.getUser(this) != null) {
			startActivity(MainActivity.createIntent(applicationContext))
			finish()
		}
		signInButton.setOnClickListener {
			showProgress()
			signIn()
		}

		viewModel.getUser().observe(this) {
			it ?: return@observe
			IVan.setUser(applicationContext, it)
			startActivity(MainActivity.createIntent(applicationContext))
			finish()
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
						viewModel.setUid(uid)
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
