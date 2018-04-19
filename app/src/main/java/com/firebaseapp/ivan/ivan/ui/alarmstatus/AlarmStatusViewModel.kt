package com.firebaseapp.ivan.ivan.ui.alarmstatus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.api.AlarmStatusApi
import com.firebaseapp.ivan.ivan.model.AlarmStatus
import com.firebaseapp.ivan.ivan.model.api.request.ReportFalseAlarmRequest
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.livedata.FirebaseLiveData
import com.firebaseapp.ivan.util.switchMap
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * @author phompang on 13/4/2018 AD.
 */
class AlarmStatusViewModel @Inject constructor(private val alarmStatusApi: AlarmStatusApi) : ViewModel() {
	private val alarmStatusRef = FirebaseDatabase.getInstance().reference.child("alarm_status_data")
	private val reportFalseResult = MutableLiveData<Boolean>()
	private val alarmStatusUid = MutableLiveData<String>()
	private val alarmStatus = alarmStatusUid.switchMap {
		FirebaseLiveData(alarmStatusRef.child(it), deserializer<AlarmStatus>()).getLiveData()
	}

	init {
		reportFalseResult.value = false
	}

	fun setAlarmStatusUid(uid: String) {
		this.alarmStatusUid.value = uid
	}

	fun getAlarmStatus(): LiveData<AlarmStatus> {
		return alarmStatus
	}

	fun getReportFalseResult() = reportFalseResult

	fun setReportFalseResult(result: Boolean) {
		this.reportFalseResult.value = result
	}

	fun reportFalseAlarm(uid: String) {
		alarmStatusApi.reportFalseAlarm(ReportFalseAlarmRequest(uid)).enqueue(object : Callback<String> {
			override fun onFailure(call: Call<String>?, t: Throwable?) {
				Timber.e(t)
				reportFalseResult.value = false
			}

			override fun onResponse(call: Call<String>?, response: Response<String>?) {
				reportFalseResult.value = true
			}
		})
	}
}