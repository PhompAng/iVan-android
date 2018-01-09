package com.firebaseapp.ivan.ivan.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.utils.observe
import com.firebaseapp.ivan.ivan.utils.toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mAuth = FirebaseAuth.getInstance()
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
        viewModel.signIn(email, password).observe(this) { t: Task<AuthResult>? ->
            t ?: return@observe
            when (t.isSuccessful) {
                true -> {
                    Timber.d("success")
                }
                else -> {
                    Timber.e(t.exception)
                    toast("signIn ${t.exception}")
                }
            }
        }
    }
}
