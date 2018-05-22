package com.firebaseapp.ivan.ivan.api

import com.firebaseapp.ivan.ivan.model.api.request.MobilityRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author phompang on 16/1/2018 AD.
 */
interface MobilityApi {
	@POST("status")
	fun postMobilityStatus(@Body body: MobilityRequest): Call<String>
}