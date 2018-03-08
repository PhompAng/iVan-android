package com.firebaseapp.ivan.ivan.ui.parent

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 20/2/2018 AD.
 */
class ParentViewModel : ViewModel() {
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")

	private var parentUid = MutableLiveData<String>()
	private var parent = parentUid.switchMap {
		FirebaseLiveData(parentRef.child(it), deserializer<Parent>()).getLiveData()
	}

	fun setParentUid(uid: String) {
		this.parentUid.value = uid
	}

	fun getParent() = parent
}