package com.firebaseapp.ivan.ivan.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

/**
 * @author phompang on 14/1/2018 AD.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Location(@get:PropertyName("lat") @set:PropertyName("lat") var lat: Double = 0.0,
					@get:PropertyName("lng") @set:PropertyName("lng") var lng: Double = 0.0) : FirebaseModel(), Parcelable