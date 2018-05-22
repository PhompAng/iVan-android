package com.firebaseapp.ivan.ivan.ui.carmap.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.MobilityStatus
import com.firebaseapp.ivan.util.getRelativeTime
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_car_condition.view.*

/**
 * @author phompang on 7/4/2018 AD.
 */
class CarConditionViewHolderFactory : ViewHolderFactory<MobilityStatus> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return CarConditionViewHolder(parent!!.inflate(R.layout.view_item_car_condition))
	}

	private inner class CarConditionViewHolder(container: View) : NormalViewHolder<MobilityStatus>(container) {
		override fun fillData(data: MobilityStatus, position: Int) {
			itemView.carConditionTextView.text = String.format("%.2f★", data.star)
			itemView.lastUpdateTextView.text = data.timestamp.getRelativeTime(getContext())
		}
	}
}