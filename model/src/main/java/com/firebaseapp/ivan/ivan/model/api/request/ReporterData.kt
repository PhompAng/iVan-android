package com.firebaseapp.ivan.ivan.model.api.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author phompang on 26/4/2018 AD.
 */
@Parcelize
data class ReporterData(val uid: String = "",
						val fullName: String = "",
						val role: Int = 0) : Parcelable