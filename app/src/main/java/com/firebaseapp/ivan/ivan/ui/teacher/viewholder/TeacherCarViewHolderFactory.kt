package com.firebaseapp.ivan.ivan.ui.teacher.viewholder

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
 * @author phompang on 6/4/2018 AD.
 */
class TeacherCarViewHolderFactory : ViewHolderFactory<Car> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return TeacherCarViewHolder(parent!!.inflate(R.layout.view_item_driver_car))
	}

	private inner class TeacherCarViewHolder(container: View) : NormalViewHolder<Car>(container) {
		override fun fillData(data: Car, position: Int) {
			itemView.carContactView.hint = data.province
			itemView.carContactView.fillData(data.plateNumber)
		}
	}
}