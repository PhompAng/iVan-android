package com.firebaseapp.ivan.ivan.delegate

import android.content.Context
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.MobilityStatus
import java.util.*

/**
 * @author phompang on 3/2/2018 AD.
 */
data class DelegateMobilityStatus(val title: String = "",
								  val valueText: String = "",
								  val value: Float = 0.0F,
								  val maxProgress: Float = 100.0F,
								  val timestamp: Long = 0) {
	companion object {
		fun getAvgSpeed(context: Context, mobilityStatus: MobilityStatus): DelegateMobilityStatus {
			return DelegateMobilityStatus(
					context.getString(R.string.avg_speed),
					context.getString(R.string.avg_speed_value, mobilityStatus.speed.toFloat()),
					mobilityStatus.speed.toFloat(),
					120F,
					mobilityStatus.timestamp
			)
		}

		fun getOilLevel(context: Context, mobilityStatus: MobilityStatus): DelegateMobilityStatus {
			return DelegateMobilityStatus(
					context.getString(R.string.oil_level),
					context.getString(R.string.percent_value, mobilityStatus.oilLevel.toFloat()),
					mobilityStatus.oilLevel.toFloat(),
					100F,
					mobilityStatus.timestamp
			)
		}
	}
}