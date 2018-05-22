package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import com.firebaseapp.ivan.glide.GlideApp
import com.firebaseapp.ivan.ivan.model.Location
import com.firebaseapp.ivan.ivan.model.delegate.DelegateAlarmStatus
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.R
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.createStaticMapUrl
import kotlinx.android.synthetic.main.view_detected_overlay.view.*

/**
 * @author phompang on 10/4/2018 AD.
 */
class DetectedOverlay @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : AbstractCustomView<DelegateAlarmStatus>(context, attrs, defStyleAttr, defStyleRes) {

	private val user by lazy {
		IVan.getUser(context)
	}

	var onOverlayClickListener: OnOverlayClickListener? = null

	override fun getContentLayout(): Int = R.layout.view_detected_overlay

	override fun fillDataNonNull(d: DelegateAlarmStatus) {
		titleTextView.text = d.title
		text1.text = d.text
		GlideApp.with(context)
				.load(Location(d.lat, d.lng).createStaticMapUrl(convertToPx(context, 300), convertToPx(context, 120), 17 ,2))
				.into(staticMapView)

		user.fold {
			onParent {
				detailButton.visibility = View.GONE
			}
		}

		negativeButton.setOnClickListener {
			onOverlayClickListener?.onNegativeClick()
		}
		openMapButton.setOnClickListener {
			onOverlayClickListener?.onOpenMapClick()
		}
		detailButton.setOnClickListener {
			onOverlayClickListener?.onDetailClick()
		}
	}

	interface OnOverlayClickListener {
		fun onNegativeClick()
		fun onDetailClick()
		fun onOpenMapClick()
	}
}