package com.firebaseapp.ivan.livedataadapter.model

import android.util.Log
import retrofit2.Response
import java.io.IOException

/**
 * @author phompang on 16/1/2018 AD.
 */
class ApiResponse<T> {
	val code: Int
	val body: T?
	val errorMessage: String?

	constructor(error: Throwable) {
		code = 500
		body = null
		errorMessage = error.message
	}

	constructor(response: Response<T>) {
		code = response.code()
		if (response.isSuccessful) {
			body = response.body()
			errorMessage = null
		} else {
			var message: String? = null
			if (response.errorBody() != null) {
				try {
					message = response.errorBody()!!.string()
				} catch (ignored: IOException) {
					Log.e("Parse", "error while parsing response")
				}

			}
			if (message == null || message.trim { it <= ' ' }.isEmpty()) {
				message = response.message()
			}
			errorMessage = message
			body = null
		}
	}

	val isSuccessful: Boolean
		get() = code in 200..299
}