package com.firebaseapp.ivan.ivan.ui.students

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.School
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentsViewModel : ViewModel() {
	private val schoolRef = FirebaseDatabase.getInstance().reference.child("schools")

	private var car = MutableLiveData<Car>()
	private var school = car.switchMap {
		FirebaseLiveData(schoolRef.child(it.school), deserializer<School>()).getLiveData()
	}

}