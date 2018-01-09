package com.firebaseapp.ivan.ivan.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * @author phompang on 9/1/2018 AD.
 */
class LoginViewModel : ViewModel() {
    val mAuth = FirebaseAuth.getInstance()
    val result: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    fun signIn(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    result.postValue(task)
                }
        return result
    }
}