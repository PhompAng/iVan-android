package com.firebaseapp.ivan.ivan.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * @author phompang on 21/1/2018 AD.
 */
class MainViewModel : ViewModel() {
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")
	private var parentUid = MutableLiveData<String>()
	private var parent = parentUid.switchMap {
		FirebaseLiveData(parentRef.child(it), deserializer<Parent>()).getLiveData()
	}

	fun setUid(uid: String) {
		this.parentUid.value = uid
	}

	fun getParent(): LiveData<Parent> {
		return parent
	}
}