package com.firebaseapp.ivan.ivan.ui.alarmstatus.holder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.AlarmStatusData
import com.firebaseapp.ivan.ivan.model.SensorData
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_alarm_status_data.view.*

/**
 * @author phompang on 14/4/2018 AD.
 */
class AlarmStatusDataViewHolderFactory : ViewHolderFactory<AlarmStatusData> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return AlarmStatusDataViewHolder(parent!!.inflate(R.layout.view_item_alarm_status_data))
	}

	private inner class AlarmStatusDataViewHolder(container: View) : NormalViewHolder<AlarmStatusData>(container) {
		override fun fillData(data: AlarmStatusData, position: Int) {
			itemView.rowTextView.text = getContext().getString(R.string.alarm_status_row, data.row)
			itemView.detectionTextView.text = data.detection
		}
	}
}