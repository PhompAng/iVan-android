package com.firebaseapp.ivan.ivan.ui.alarmstatus

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author phompang on 17/4/2018 AD.
 */
@Module
internal abstract class AlarmStatusModule {
	@ContributesAndroidInjector
	abstract fun contributeAlarmStatusFragment(): AlarmStatusFragment
}