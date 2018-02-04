package com.firebaseapp.ivan.ivan.service

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by phompang on 12/13/2017 AD.
 */

class MyFirebaseInstanceIDService: FirebaseInstanceIdService() {

	companion object {
		private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
	}

	override fun onTokenRefresh() {
		super.onTokenRefresh()
		val refreshedToken = FirebaseInstanceId.getInstance().token
		Log.d(TAG, "Refreshed token: " + refreshedToken!!)
	}
}
