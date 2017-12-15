package com.firebaseapp.ivan.ivan.api.model

/**
 * Created by phompang on 12/14/2017 AD.
 */
data class MobilityRequest(val car_id: String,
                           val mobility_status: MobilityStatus,
                           val car_plate_id: String = "dd-1234")

data class MobilityStatus(val timestamp: Long,
                          val lat: Double,
                          val lng: Double,
                          val speed: Double = 123.0,
                          val oil_level: Double = 48.0)
