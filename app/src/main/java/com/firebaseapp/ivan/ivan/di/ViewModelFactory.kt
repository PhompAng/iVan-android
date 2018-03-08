package com.firebaseapp.ivan.ivan.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.firebaseapp.ivan.ivan.ui.main.MainViewModel
import com.firebaseapp.ivan.ivan.ui.carmap.CarViewModel
import com.firebaseapp.ivan.ivan.ui.notification.NotificationViewModel
import com.firebaseapp.ivan.ivan.ui.parent.ParentViewModel
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
					isAssignableFrom(NotificationViewModel::class.java) -> NotificationViewModel()
					isAssignableFrom(ParentViewModel::class.java) -> ParentViewModel()
					else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
				} as T
			}
}