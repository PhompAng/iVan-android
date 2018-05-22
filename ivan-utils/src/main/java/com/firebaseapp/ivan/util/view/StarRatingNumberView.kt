package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.StyleRes
import android.util.AttributeSet
import com.firebaseapp.ivan.util.R
import kotlinx.android.synthetic.main.view_star_rating_number.view.*

/**
 * @author phompang on 25/2/2018 AD.
 */
class StarRatingNumberView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : AbstractCustomView<Float>(context, attrs, defStyleAttr, defStyleRes) {

	override fun getContentLayout() = R.layout.view_star_rating_number

	override fun bindView() {
		ratingTextView.setPadding(0, 0, 0, 0)
		ratingTextView.includeFontPadding = false
		ratingTextView.typeface = resources.getFont(R.font.kanit_regular)
		ratingTextView.text = "-"
	}

	override fun fillDataInEditMode() {
		ratingTextView.text = "-"
	}

	override fun fillDataNonNull(d: Float) {
		val roundedValue = Math.round(d * 10f) / 10f
		if (roundedValue > 0.0) {
			ratingTextView.text = String.format("%.1f", roundedValue)
			backgroundView.setImageLevel((roundedValue * 10).toInt().coerceIn(0, 50))
			visibility = VISIBLE
		} else {
			visibility = GONE
		}
	}
}