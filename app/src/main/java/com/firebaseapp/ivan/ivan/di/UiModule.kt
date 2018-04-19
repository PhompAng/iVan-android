package com.firebaseapp.ivan.ivan.di

import com.firebaseapp.ivan.ivan.ui.alarmstatus.AlarmStatusActivity
import com.firebaseapp.ivan.ivan.ui.alarmstatus.AlarmStatusModule
import com.firebaseapp.ivan.ivan.ui.main.MainActivity
import com.firebaseapp.ivan.ivan.ui.main.MainModule
import com.firebaseapp.ivan.ivan.ui.parent.ParentActivity
import com.firebaseapp.ivan.ivan.ui.parent.ParentModule
import com.firebaseapp.ivan.ivan.ui.student.StudentActivity
import com.firebaseapp.ivan.ivan.ui.student.StudentModule
import com.firebaseapp.ivan.ivan.ui.teacher.TeacherActivity
import com.firebaseapp.ivan.ivan.ui.teacher.TeacherModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 16/1/2018 AD.
 */
@Module
internal abstract class UiModule {
	@ContributesAndroidInjector(modules = [MainModule::class])
	internal abstract fun contributeMainActivity(): MainActivity

	@ContributesAndroidInjector(modules = [StudentModule::class])
	internal abstract fun contributeStudentActivity(): StudentActivity

	@ContributesAndroidInjector(modules = [TeacherModule::class])
	internal abstract fun contributeTeacherActivity(): TeacherActivity

	@ContributesAndroidInjector(modules = [ParentModule::class])
	internal abstract fun contributeParentActivity(): ParentActivity

	@ContributesAndroidInjector(modules = [AlarmStatusModule::class])
	internal abstract fun contributeAlarmStatusActivity(): AlarmStatusActivity
}