package com.firebaseapp.ivan.ivan.api

import com.firebaseapp.ivan.ivan.model.api.request.ConfirmSecuredRequest
import com.firebaseapp.ivan.ivan.model.api.request.ReportFalseAlarmRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author phompang on 17/4/2018 AD.
 */
interface AlarmStatusApi {
	@POST("report_false")
	fun reportFalseAlarm(@Body body: ReportFalseAlarmRequest): Call<String>

	@POST("confirm")
	fun confirmSecured(@Body body: ConfirmSecuredRequest): Call<String>
}