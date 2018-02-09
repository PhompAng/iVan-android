package com.firebaseapp.ivan.ivan.ui.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.model.*
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.map
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * @author phompang on 22/1/2018 AD.
 */
class CarViewModel : ViewModel() {
	private val carRef = FirebaseDatabase.getInstance().reference.child("cars")
	private val schoolRef = FirebaseDatabase.getInstance().reference.child("schools")

	private var car = MutableLiveData<Car>()
	private var status = car
			.switchMap {
				FirebaseLiveData(carRef.child(it.getKeyOrId()).child("mobility_status").limitToLast(1), listDeserializer<MobilityStatus>()).getLiveData()
			}
			.map {
				if (it.isEmpty()) {
					return@map null
				} else {
					return@map it[0]
				}
			}
	private var school = car.switchMap {
		FirebaseLiveData(schoolRef.child(it.school), deserializer<School>()).getLiveData()
	}

	fun setCar(car: Car) {
		this.car.value = car
	}

	fun getMobilityStatus(): LiveData<MobilityStatus?> = status

	fun getSchool(): LiveData<School> = school
}