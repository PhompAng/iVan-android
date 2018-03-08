package com.firebaseapp.ivan.ivan.ui.notification

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Notification
import com.firebaseapp.ivan.ivan.model.listDeserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 13/2/2018 AD.
 */
class NotificationViewModel : ViewModel() {
	private val notificationRef = FirebaseDatabase.getInstance().reference.child("notifications")

	private var userId = MutableLiveData<String>()
	private var notifications = userId.switchMap {
		FirebaseLiveData(notificationRef.child(it).limitToLast(20), listDeserializer<Notification>()).getLiveData()
	}

	fun setUserId(userId: String) {
		this.userId.value = userId
	}

	fun getNotifications(): LiveData<List<Notification>> = notifications
}