package com.firebaseapp.ivan.ivan.ui.driver.viewholder

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.view.OnTypeClickListener
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_driver_contact.view.*
import org.jetbrains.anko.email
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast

/**
 * @author phompang on 13/2/2018 AD.
 */
class DriverContactViewHolderFactory : ViewHolderFactory<Car> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Car> {
		return DriverContactViewHolder(parent!!.inflate(R.layout.view_item_driver_contact))
	}

	inner class DriverContactViewHolder(container: View) : NormalViewHolder<Car>(container) {
		override fun fillData(data: Car, position: Int) {
			itemView.phoneContactView.setOnClickListener(OnTypeClickListener { _, t ->
				t?.let {
					try {
						getContext().makeCall(it)
					} catch (e: ActivityNotFoundException) {
						getContext().toast(R.string.msg_no_phone)
					}
				}
			})
			itemView.emailContactView.setOnClickListener(OnTypeClickListener { _, t ->
				t?.let {
					try {
						getContext().email(t)
					} catch (e: ActivityNotFoundException) {
						getContext().toast(R.string.msg_no_email)
					}
				}
			})

			itemView.phoneContactView.fillData(data.drivers[0].telephone)
			itemView.emailContactView.fillData(data.drivers[0].email)
		}
	}
}