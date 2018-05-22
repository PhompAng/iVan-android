package com.firebaseapp.ivan.util

import android.content.Context
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.firebaseapp.ivan.ivan.model.Location
import com.google.android.gms.maps.model.LatLng

/**
 * @author phompang on 8/3/2018 AD.
 */

fun distanceBetween(location1: Location?, location2: Location?): Float? {
	if (location1 == null || location2 == null) {
		return null
	}
	//TODO Google Direction
	val loc1 = createAndroidLocation(location1)
	val loc2 = createAndroidLocation(location2)
	return loc1.distanceTo(loc2)
}

fun estimateTime(context: Context, location1: Location?, location2: Location?, onSuccess: (Direction?, String?) -> Unit, onError: (Throwable?) -> Unit = {}) {
	if (location1 == null || location2 == null) {
		return
	}
	GoogleDirection.withServerKey(context.getString(R.string.server_key))
			.from(createLatLng(location1))
			.to(createLatLng(location2))
			.transportMode(TransportMode.DRIVING)
			.execute(object : DirectionCallback {
				override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
					when (direction?.isOK) {
						true -> onSuccess(direction, rawBody)
						else -> context.toast("Direction error ${direction?.errorMessage}")
					}
				}

				override fun onDirectionFailure(t: Throwable?) {
					onError(t)
				}
			})
}

fun createAndroidLocation(location: Location) =
		android.location.Location("").apply {
			latitude = location.lat
			longitude = location.lng
		}

fun createLatLng(location: Location) =
		LatLng(location.lat, location.lng)