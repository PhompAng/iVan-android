package com.firebaseapp.ivan.ivan.ui.select

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.model.listDeserializer
import com.firebaseapp.ivan.util.filter
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase

/**
 * @author phompang on 16/1/2018 AD.
 */
class SelectCarViewModel : ViewModel() {
	private val carsRef = FirebaseDatabase.getInstance().reference.child("cars")
	private val studentRef = FirebaseDatabase.getInstance().reference.child("students")

	private var uid: MutableLiveData<String> = MutableLiveData()
	private var students: LiveData<List<Student>> = uid.switchMap {
		FirebaseLiveData(studentRef.orderByChild("parent").equalTo(it), listDeserializer<Student>()).getLiveData()
	}
	private var cars = students
			.switchMap {
				val data = FirebaseLiveData(carsRef, listDeserializer<Car>()).getLiveData()
				data.filter {
					students.value?.forEach { student: Student ->
						if (student.car == it.id) {
							return@filter true
						}
					}
					return@filter false
				}
			}

	fun setUid(uid: String) {
		this.uid.value = uid
	}

	fun getCars(): LiveData<List<Car>> {
		return cars
	}
}