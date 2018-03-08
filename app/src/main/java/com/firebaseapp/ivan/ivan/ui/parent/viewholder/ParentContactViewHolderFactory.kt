package com.firebaseapp.ivan.ivan.ui.parent.viewholder

import android.content.ActivityNotFoundException
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.view.OnTypeClickListener
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_contact.view.*
import org.jetbrains.anko.email
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast

/**
 * @author phompang on 20/2/2018 AD.
 */
class ParentContactViewHolderFactory : ViewHolderFactory<Parent> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Parent> {
		return ParentContactViewHolder(parent!!.inflate(R.layout.view_item_contact))
	}

	inner class ParentContactViewHolder(container: View) : NormalViewHolder<Parent>(container) {
		override fun fillData(data: Parent, position: Int) {
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

			itemView.phoneContactView.fillData(data.telephone)
			itemView.emailContactView.fillData(data.email)
		}
	}
}