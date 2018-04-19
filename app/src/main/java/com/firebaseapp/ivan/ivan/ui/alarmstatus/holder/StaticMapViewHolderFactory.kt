package com.firebaseapp.ivan.ivan.ui.alarmstatus.holder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.glide.GlideApp
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.AlarmStatus
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.createStaticMapUrl
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_alarm_map.view.*

/**
 * @author phompang on 13/4/2018 AD.
 */
class StaticMapViewHolderFactory : ViewHolderFactory<AlarmStatus> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return StaticMapViewHolder(parent!!.inflate(R.layout.view_item_alarm_map))
	}

	private inner class StaticMapViewHolder(container: View) : NormalViewHolder<AlarmStatus>(container) {
		private val width by lazy {
			convertToPx(getContext(), 360)
		}
		private val height by lazy {
			convertToPx(getContext(), 200)
		}

		override fun fillData(data: AlarmStatus, position: Int) {
			GlideApp.with(getContext()).load(data.location.createStaticMapUrl(width, height, 17, 2)).centerCrop().into(itemView.staticMapView)
		}
	}
}