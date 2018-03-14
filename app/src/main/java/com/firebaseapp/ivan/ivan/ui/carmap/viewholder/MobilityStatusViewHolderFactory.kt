package com.firebaseapp.ivan.ivan.ui.carmap.viewholder

import android.animation.ArgbEvaluator
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.delegate.DelegateMobilityStatus
import com.firebaseapp.ivan.util.getRelativeTime
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_mobility_status.view.*

/**
 * @author phompang on 3/2/2018 AD.
 */
class MobilityStatusViewHolderFactory(
		@ColorRes private val minColorRes: Int = R.color.colorPrimary,
		@ColorRes private val maxColorRes: Int = R.color.colorPrimary)
	: ViewHolderFactory<DelegateMobilityStatus> {

	override fun create(parent: ViewGroup?): ItemViewHolder<DelegateMobilityStatus> {
		return MobilityStatusViewHolder(
				LayoutInflater.from(
						parent?.context).inflate(R.layout.view_item_mobility_status, parent, false))
	}

	inner class MobilityStatusViewHolder(containerView: View) : NormalViewHolder<DelegateMobilityStatus>(containerView) {
		private val argbEvaluator = ArgbEvaluator()
		private val minColor = ContextCompat.getColor(getContext(), minColorRes)
		private val maxColor = ContextCompat.getColor(getContext(), maxColorRes)

		override fun fillData(data: DelegateMobilityStatus, position: Int) {
			itemView.titleTextView.text = data.title
			itemView.valueTextView.text = data.valueText

			val percent = 100 * data.value / data.maxProgress

			itemView.lastUpdateTextView.text = data.timestamp.getRelativeTime(getContext())

			val color: Int = argbEvaluator.evaluate(percent / 100, minColor, maxColor) as Int
			itemView.valueTextView.setTextColor(color)
		}
	}
}