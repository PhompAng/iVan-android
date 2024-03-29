package com.firebaseapp.ivan.ivan.ui.parent.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.simpleAddress
import com.firebaseapp.ivan.ivan.ui.map.MapActivity
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.createStaticMapUrl
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_home_location.view.*
import org.jetbrains.anko.startActivity

/**
 * @author phompang on 9/2/2018 AD.
 */
class ParentHomeLocationViewHolderFactory: ViewHolderFactory<Parent> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Parent> {
		return ParentHomeLocationViewHolder(parent!!.inflate(R.layout.view_item_home_location))
	}

	inner class ParentHomeLocationViewHolder(container: View) : NormalViewHolder<Parent>(container) {
		private val width by lazy {
			convertToPx(getContext(), 360)
		}
		private val height by lazy {
			convertToPx(getContext(), 200)
		}

		override fun fillData(data: Parent, position: Int) {
			itemView.locationView.setOnClickListener {
				getContext().startActivity<MapActivity>(MapActivity.EXTRA_LOCATION to data.location)
			}
			DataBindingUtils.setImageUrl(
					itemView.staticMapView,
					data.location.createStaticMapUrl(width, height, 17, 2))
			itemView.addressTextView.text = data.address.simpleAddress()
		}
	}
}