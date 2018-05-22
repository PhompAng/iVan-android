package com.firebaseapp.ivan.livedataadapter

import android.arch.lifecycle.LiveData
import com.firebaseapp.ivan.livedataadapter.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author phompang on 16/1/2018 AD.
 */

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {
	override fun responseType(): Type {
		return responseType
	}

	override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
		return object : LiveData<ApiResponse<R>>() {
			internal var started = AtomicBoolean(false)
			override fun onActive() {
				super.onActive()
				if (started.compareAndSet(false, true)) {
					call.enqueue(object : Callback<R> {
						override fun onFailure(call: Call<R>, t: Throwable) {
							postValue(ApiResponse(t))
						}

						override fun onResponse(call: Call<R>, response: Response<R>) {
							postValue(ApiResponse(response))
						}
					})
				}
			}
		}
	}

}