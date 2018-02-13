package com.firebaseapp.ivan.ivan.ui.driver.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_driver_contact.view.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class DriverContactViewHolderFactory : ViewHolderFactory<Car> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Car> {
		return DriverContactViewHolder(parent!!.inflate(R.layout.view_item_driver_contact))
	}

	inner class DriverContactViewHolder(container: View) : NormalViewHolder<Car>(container) {
		override fun fillData(data: Car, position: Int) {
			itemView.phoneTextView.text = data.drivers[0].telephone
			itemView.emailTextView.text = data.drivers[0].email
		}
	}
}