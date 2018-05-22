package com.firebaseapp.ivan.ivan.model.api.request

import com.firebaseapp.ivan.ivan.model.Location

/**
 * @author phompang on 19/4/2018 AD.
 */
data class ConfirmSecuredData(val reporter: ReporterData,
							  val reportLocation: Location)