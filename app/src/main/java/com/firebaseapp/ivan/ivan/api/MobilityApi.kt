package com.firebaseapp.ivan.ivan.api

import com.firebaseapp.ivan.ivan.api.model.MobilityRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by phompang on 12/14/2017 AD.
 */
interface MobilityApi {
	@POST("status")
	fun postMobilityStatus(@Body body: MobilityRequest): Call<String>
}
