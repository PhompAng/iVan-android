package com.firebaseapp.ivan.ivan.ui.driver.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_driver_car.view.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class DriverCarViewHolderFactory : ViewHolderFactory<Car> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Car> {
		return DriverCarViewHolder(parent!!.inflate(R.layout.view_item_driver_car))
	}

	inner class DriverCarViewHolder(container: View) : NormalViewHolder<Car>(container) {
		override fun fillData(data: Car, position: Int) {
			itemView.carPlateNumberTextView.text = data.plateNumber
			itemView.carProvinceTextView.text = data.province
		}
	}
}