package com.firebaseapp.ivan.ivan.ui.select.viewholder

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.databinding.ViewItemSelectCarBinding
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.School
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.util.addValueEventListener
import com.google.firebase.database.FirebaseDatabase
import com.wongnai.android.*
import kotlinx.android.synthetic.main.view_item_select_car.view.*

/**
 * @author phompang on 21/1/2018 AD.
 */

class SelectCarViewHolderFactory(val onCarClickListener: TypeItemEventListener<Car>) : ViewHolderFactory<Car> {
	private val schoolRef = FirebaseDatabase.getInstance().reference.child("schools")

	override fun create(parent: ViewGroup?): ItemViewHolder<Car> {
		return SelectCarViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(parent?.context), R.layout.view_item_select_car, parent, false
				))
	}

	inner class SelectCarViewHolder(private val selectCarBinding: ViewItemSelectCarBinding) : DataBindingItemViewHolder<Car>(selectCarBinding) {
		override fun fillData(data: Car, position: Int) {
			selectCarBinding.setVariable(BR.car, data)

			schoolRef.child(data.school).addValueEventListener {
				onDataChange {
					val school = deserializer<School>().apply(it)
					selectCarBinding.setVariable(BR.school, school)
					selectCarBinding.executePendingBindings()
				}
			}
			selectCarBinding.root.setOnClickListener {
				onCarClickListener.onItemClick(it, data, position)
			}
		}
	}
}