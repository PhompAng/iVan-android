package com.firebaseapp.ivan.ivan.ui.notification.viewholder

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Notification
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_notification.view.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class NotificationViewHolderFactory(private val onNotificationClickListener: OnNotificationClickListener) : ViewHolderFactory<Notification> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Notification> {
		return NotificationViewHolder(parent!!.inflate(R.layout.view_item_notification))
	}

	inner class NotificationViewHolder(container: View) : NormalViewHolder<Notification>(container) {
		override fun fillData(data: Notification, position: Int) {
			itemView.titleTextView.text = "ALERT!!"
			itemView.descriptionTextView.text = data.text
			itemView.timestampTextView.text = DateUtils.getRelativeTimeSpanString(getContext(), data.timestamp)

			itemView.setOnClickListener {
				onNotificationClickListener.onClick(data)
			}
		}
	}

	interface OnNotificationClickListener {
		fun onClick(notification: Notification)
	}
}