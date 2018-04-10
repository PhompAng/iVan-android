package com.firebaseapp.ivan.ivan.ui.carmap.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.Driver
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.*
import kotlinx.android.synthetic.main.view_item_car_staff_list.view.*

/**
 * @author phompang on 7/4/2018 AD.
 */
class CarStaffListViewHolderFactory : ViewHolderFactory<Car> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return CarStaffListViewHolder(parent!!.inflate(R.layout.view_item_car_staff_list))
	}

	private inner class CarStaffListViewHolder(container: View) : NormalViewHolder<Car>(container) {

		val adapter = MultipleViewAdapter(1)

		init {
			adapter.registerViewHolderFactory(TYPE_0, CarStaffViewHolderFactory<Driver>())
			adapter.registerViewHolderFactory(TYPE_1, CarStaffViewHolderFactory<Teacher>())

			itemView.recyclerView.layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
			itemView.recyclerView.adapter = adapter
		}
		override fun fillData(data: Car, position: Int) {
			adapter.clear()
			adapter.addAll(data.drivers, TYPE_0)
			adapter.addAll(data.teachers, TYPE_1)
		}
	}
}