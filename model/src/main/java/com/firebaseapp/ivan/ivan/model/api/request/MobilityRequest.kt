package com.firebaseapp.ivan.ivan.model.api.request

import com.firebaseapp.ivan.ivan.model.MobilityStatus

/**
 * @author phompang on 16/1/2018 AD.
 */

data class MobilityRequest(val car_id: String = "",
						   val mobility_status: MobilityStatus = MobilityStatus(),
						   val car_plate_id: String = "")