package com.firebaseapp.ivan.ivan.ui.student

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.School
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 9/2/2018 AD.
 */
class StudentViewModel : ViewModel() {
	private val studentRef = FirebaseDatabase.getInstance().reference.child("students")
	private val schoolRef = FirebaseDatabase.getInstance().reference.child("schools")
	private val parentRef = FirebaseDatabase.getInstance().reference.child("parents")

	private var studentUid = MutableLiveData<String>()
	private var student = studentUid.switchMap {
		FirebaseLiveData(studentRef.child(it), deserializer<Student>()).getLiveData()
	}
	private var school = student.switchMap {
		FirebaseLiveData(schoolRef.child(it.school), deserializer<School>()).getLiveData()
	}
	private var parent = student.switchMap {
		FirebaseLiveData(parentRef.child(it.parent), deserializer<Parent>()).getLiveData()
	}

	fun setStudentUid(uid: String) {
		this.studentUid.value = uid
	}

	fun getStudent(): LiveData<Student> = student

	fun getSchool(): LiveData<School> = school

	fun getParent(): LiveData<Parent> = parent
}