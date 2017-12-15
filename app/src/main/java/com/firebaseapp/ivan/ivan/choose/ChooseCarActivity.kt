package com.firebaseapp.ivan.ivan.choose

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging

class ChooseCarActivity : AppCompatActivity() {

	private lateinit var mDatabaseRef: DatabaseReference
	private val students = mutableListOf<Student>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_choose_car)

		mDatabaseRef = FirebaseDatabase.getInstance().reference
		val user = FirebaseAuth.getInstance().currentUser
		user?.let {
			loadStudent(user)
		}
	}

	private fun loadStudent(user: FirebaseUser) {
		mDatabaseRef.child("students")
				.orderByChild("parent")
				.equalTo(user.uid)
				.addListenerForSingleValueEvent(object : ValueEventListener {
					override fun onCancelled(p0: DatabaseError?) {

					}

					override fun onDataChange(dataSnapshot: DataSnapshot?) {
						dataSnapshot?.let {
							students.clear()
							dataSnapshot.children.mapNotNullTo(students) {
								it.getValue<Student>(Student::class.java)
							}
							loadCar()
						}
					}
				})
	}

	private fun loadCar() {
		for (student in students) {
			Log.d("car", "LoadCar" + student)
			if (!student.car.isNullOrBlank()) {
				FirebaseMessaging.getInstance().subscribeToTopic(student.car)
			}
		}
	}
}
