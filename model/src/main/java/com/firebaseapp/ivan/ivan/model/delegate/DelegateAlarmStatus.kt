package com.firebaseapp.ivan.ivan.model.delegate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author phompang on 10/4/2018 AD.
 */
@Parcelize
data class DelegateAlarmStatus(val title: String,
							   val text: String,
							   val carId: String,
							   val lat: Double,
							   val lng: Double,
							   val uid: String) : Parcelable