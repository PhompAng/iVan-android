package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import com.firebaseapp.ivan.util.R
import kotlinx.android.synthetic.main.view_contact.view.*
import timber.log.Timber

/**
 * @author phompang on 17/2/2018 AD.
 */
class ContactView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : AbstractCustomView<String>(context, attrs, defStyleAttr, defStyleRes) {

	var isShowDivider: Boolean? = null
	var hint: String? = null
	@DrawableRes var icon = 0

	override fun extractAttribute(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
		val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ContactView, defStyleAttr, defStyleRes)
		try {
			isShowDivider = typedArray.getBoolean(
					R.styleable.ContactView_cv_divider,
					true)
			hint = context.getString(typedArray.getResourceId(
					R.styleable.ContactView_cv_hint,
					R.string.phone))
			icon = typedArray.getResourceId(R.styleable.ContactView_cv_icon, R.drawable.ic_error_outline_black_24dp)
		} finally {
			typedArray.recycle()
		}
	}

	override fun fillDataInEditMode() {
		dataTextView.text = "0812345678"
		dataHintTextView.text = hint
		divider.visibility = if (isShowDivider == true) View.VISIBLE else View.INVISIBLE
		iconImageView.setImageResource(icon)
	}

	override fun fillDataNonNull(d: String) {
		dataTextView.text = d
		dataHintTextView.text = hint
		divider.visibility = if (isShowDivider == true) View.VISIBLE else View.INVISIBLE
		iconImageView.setImageResource(icon)
	}

	override fun getContentLayout(): Int = R.layout.view_contact

}