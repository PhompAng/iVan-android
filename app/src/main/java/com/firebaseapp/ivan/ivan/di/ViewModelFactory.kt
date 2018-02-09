package com.firebaseapp.ivan.ivan.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.firebaseapp.ivan.ivan.ui.main.MainViewModel
import com.firebaseapp.ivan.ivan.ui.map.CarViewModel
import com.firebaseapp.ivan.ivan.ui.select.SelectCarViewModel
import com.firebaseapp.ivan.ivan.ui.student.StudentViewModel
import com.firebaseapp.ivan.ivan.ui.students.StudentsViewModel

/**
 * @author phompang on 16/1/2018 AD.
 */
class ViewModelFactory : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T =
			with(modelClass) {
				when {
					isAssignableFrom(MainViewModel::class.java) -> MainViewModel()
					isAssignableFrom(CarViewModel::class.java) -> CarViewModel()
					isAssignableFrom(SelectCarViewModel::class.java) -> SelectCarViewModel()
					isAssignableFrom(StudentsViewModel::class.java) -> StudentsViewModel()
					isAssignableFrom(StudentViewModel::class.java) -> StudentViewModel()
					else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
				} as T
			}
}