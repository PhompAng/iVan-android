package com.firebaseapp.ivan.ivan.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.firebaseapp.ivan.ivan.ui.alarmstatus.AlarmStatusViewModel
import com.firebaseapp.ivan.ivan.ui.carmap.CarMapViewModel
import com.firebaseapp.ivan.ivan.ui.main.MainViewModel
import com.firebaseapp.ivan.ivan.ui.notification.NotificationViewModel
import com.firebaseapp.ivan.ivan.ui.parent.ParentViewModel
import com.firebaseapp.ivan.ivan.ui.select.SelectCarViewModel
import com.firebaseapp.ivan.ivan.ui.student.StudentViewModel
import com.firebaseapp.ivan.ivan.ui.students.StudentsViewModel
import com.firebaseapp.ivan.ivan.ui.teacher.TeacherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author phompang on 17/4/2018 AD.
 */
@Module
abstract class ViewModelModule {
	@Binds
	@IntoMap
	@ViewModelKey(MainViewModel::class)
	abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(StudentViewModel::class)
	abstract fun bindStudentViewModel(viewModel: StudentViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(StudentsViewModel::class)
	abstract fun bindStudentsViewModel(viewModel: StudentsViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(TeacherViewModel::class)
	abstract fun bindTeacherViewModedl(viewModel: TeacherViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ParentViewModel::class)
	abstract fun bindParentViewModedl(viewModel: ParentViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(AlarmStatusViewModel::class)
	abstract fun bindAlarmStatusViewModedl(viewModel: AlarmStatusViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(SelectCarViewModel::class)
	abstract fun bindSelectCarViewModel(viewModel: SelectCarViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CarMapViewModel::class)
	abstract fun bindCarMapViewModel(viewModel: CarMapViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(NotificationViewModel::class)
	abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}