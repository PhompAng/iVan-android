package com.firebaseapp.ivan.ivan.ui.select.viewholder

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.databinding.ViewItemSelectCarBinding
import com.firebaseapp.ivan.ivan.delegate.DelegateCar
import com.firebaseapp.ivan.ivan.model.MobilityStatus
import com.firebaseapp.ivan.ivan.model.School
import com.firebaseapp.ivan.ivan.model.deserializer
import com.firebaseapp.ivan.ivan.model.listDeserializer
import com.firebaseapp.ivan.util.addListenerForSingleValueEvent
import com.google.firebase.database.FirebaseDatabase
import com.wongnai.android.*

/**
 * @author phompang on 21/1/2018 AD.
 */

class SelectCarViewHolderFactory(val onCarClickListener: TypeItemEventListener<DelegateCar>) : ViewHolderFactory<DelegateCar> {
	private val schoolRef = FirebaseDatabase.getInstance().reference.child("schools")
	private val mobilityStatusRef = FirebaseDatabase.getInstance().reference.child("mobility_status")

	override fun create(parent: ViewGroup?): ItemViewHolder<DelegateCar> {
		return SelectCarViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(parent?.context), R.layout.view_item_select_car, parent, false
				))
	}

	inner class SelectCarViewHolder(private val selectCarBinding: ViewItemSelectCarBinding) : DataBindingItemViewHolder<DelegateCar>(selectCarBinding) {
		override fun fillData(data: DelegateCar, position: Int) {
			selectCarBinding.setVariable(BR.car, data.car)
			selectCarBinding.setVariable(BR.parent, data.parent)

			schoolRef.child(data.car.school).addListenerForSingleValueEvent {
				onDataChange {
					val school = deserializer<School>().apply(it)
					selectCarBinding.setVariable(BR.school, school)
					selectCarBinding.executePendingBindings()
				}
			}

			mobilityStatusRef.child(data.car.school).child(data.car.getKeyOrId()).limitToLast(1).addListenerForSingleValueEvent {
				onDataChange {
					val mobilityStatus = listDeserializer<MobilityStatus>().apply(it)
					if (mobilityStatus.isNotEmpty()) {
						selectCarBinding.setVariable(BR.mobilityStatus, mobilityStatus.last())
					}
					selectCarBinding.executePendingBindings()
				}
			}
			selectCarBinding.root.setOnClickListener {
				onCarClickListener.onItemClick(it, data, position)
			}
		}
	}
}