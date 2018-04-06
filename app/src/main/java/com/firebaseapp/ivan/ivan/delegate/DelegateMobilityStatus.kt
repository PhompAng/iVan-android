package com.firebaseapp.ivan.ivan.delegate

import android.content.Context
import android.support.annotation.DrawableRes
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.MobilityStatus

/**
 * @author phompang on 3/2/2018 AD.
 */
data class DelegateMobilityStatus(var title: String = "",
								  var valueText: String = "",
								  var value: Float = 0.0F,
								  var maxProgress: Float = 100.0F,
								  @DrawableRes var icon: Int = 0,
								  var timestamp: Long = 0) {
	companion object {
		fun getAvgSpeed(context: Context, mobilityStatus: MobilityStatus): DelegateMobilityStatus {
			return DelegateMobilityStatus(
					context.getString(R.string.avg_speed),
					context.getString(R.string.avg_speed_value, mobilityStatus.speed.toFloat()),
					mobilityStatus.speed.toFloat(),
					120F,
					R.drawable.ic_speed_meter,
					mobilityStatus.timestamp
			)
		}

		fun getOilLevel(context: Context, mobilityStatus: MobilityStatus): DelegateMobilityStatus {
			return DelegateMobilityStatus(
					context.getString(R.string.fuel_level),
					context.getString(R.string.percent_value, mobilityStatus.oilLevel.toFloat()),
					mobilityStatus.oilLevel.toFloat(),
					100F,
					R.drawable.ic_fuel,
					mobilityStatus.timestamp
			)
		}
	}
}