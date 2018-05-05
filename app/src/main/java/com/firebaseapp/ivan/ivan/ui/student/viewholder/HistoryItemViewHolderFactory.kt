package com.firebaseapp.ivan.ivan.ui.student.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.delegate.CarHistory
import com.firebaseapp.ivan.util.getFormattedTime
import com.firebaseapp.ivan.util.getRelativeTime
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_car_history_item.view.*

/**
 * @author phompang on 1/5/2018 AD.
 */
class HistoryItemViewHolderFactory : ViewHolderFactory<CarHistory> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return HistoryItemViewHolder(parent!!.inflate(R.layout.view_item_car_history_item))
	}

	private inner class HistoryItemViewHolder(container: View) : NormalViewHolder<CarHistory>(container) {
		override fun fillData(data: CarHistory, position: Int) {
			itemView.dateTextView.text = data.date
			if (data.morning != null) {
				itemView.pickUpTextView.visibility = View.VISIBLE
				itemView.pickUpTextView.text = getContext().getString(R.string.pickup_value, data.morning.getFormattedTime("hh:mm a"))
			} else {
				itemView.pickUpTextView.visibility = View.GONE
			}
			if (data.evening != null) {
				itemView.getOffTextView.visibility = View.VISIBLE
				itemView.getOffTextView.text = getContext().getString(R.string.getoff_value, data.evening.getFormattedTime("hh:mm a"))
			} else {
				itemView.getOffTextView.visibility = View.GONE
			}
			if (data.morning != null || data.evening != null) {
				itemView.summaryTextView.text = getContext().getString(R.string.come)
			}
		}
	}
}