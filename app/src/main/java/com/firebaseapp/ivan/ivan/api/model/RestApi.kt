package com.firebaseapp.ivan.ivan.api.model

import android.util.Log
import com.firebaseapp.ivan.ivan.api.MobilityApi
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by phompang on 12/14/2017 AD.
 */

class RestApi {
	private val mobilityApi: MobilityApi

	init {
		val retrofit = Retrofit.Builder().baseUrl("http://35.201.251.192:3000")
				.addConverterFactory(MoshiConverterFactory.create())
				.build()

		mobilityApi = retrofit.create(MobilityApi::class.java)
	}

	fun postMobility(carId: String, latlng: LatLng) {
		val req = MobilityRequest(
				carId,
				MobilityStatus(
						System.currentTimeMillis(),
						latlng.latitude,
						latlng.longitude))
		mobilityApi.postMobilityStatus(req).enqueue(object : Callback<String> {
			override fun onFailure(call: Call<String>?, t: Throwable?) {
				Log.e("restApi", t.toString())
			}

			override fun onResponse(call: Call<String>?, response: Response<String>?) {
			}
		})
	}
}
