package com.firebaseapp.ivan.ivan.ui.teacher

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * @author phompang on 6/4/2018 AD.
 */
class TeacherViewModel @Inject constructor(): ViewModel() {
	private val teacherRef = FirebaseDatabase.getInstance().reference.child("teachers")

	private var teacherUid = MutableLiveData<String>()
	private var teacher = teacherUid.switchMap {
		FirebaseLiveData(teacherRef.child(it), deserializer<Teacher>()).getLiveData()
	}

	fun setTeacherUid(uid: String) {
		this.teacherUid.value = uid
	}

	fun getTeacher() = teacher
}