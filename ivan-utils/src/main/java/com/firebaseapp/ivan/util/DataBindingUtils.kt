package com.firebaseapp.ivan.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Leg
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.firebaseapp.ivan.ivan.model.*
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.util.glide.GlideTransformClass
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.CIRCLE
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.ROUND_CORNER
import com.firebaseapp.ivan.util.glide.RoundedCornersTransformation
import com.firebaseapp.ivan.util.view.PhotoGridView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tolstykh.textviewrichdrawable.TextViewRichDrawable
import timber.log.Timber


/**
 * @author phompang on 18/1/2018 AD.
 */
object DataBindingUtils {
	@JvmStatic
	@BindingAdapter("android:src")
	fun setImageUrl(view: ImageView, url: String) {
		GlideApp.with(view.context).load(url).centerCrop().into(view)
	}

	@JvmStatic
	@BindingAdapter(value = ["storage", "placeholder", "transform"], requireAll = false)
	fun loadFromFirebaseStorage(view: ImageView, data: FirebaseModel, placeholder: Drawable?, @GlideTransformClass.GlideTransform transform: Long?) {
		val ref = FirebaseStorage.getInstance().reference
		val glide = GlideApp.with(view.context)
		var glideSetting: RequestOptions = if (placeholder == null) RequestOptions().placeholder(R.drawable.ic_photo_grey500_24dp) else RequestOptions().placeholder(placeholder)
		if (transform != null) {
			glideSetting = when (transform) {
				CIRCLE -> glideSetting.transforms(CenterCrop(), CircleCrop())
				ROUND_CORNER -> glideSetting.transforms(CenterCrop(), RoundedCornersTransformation(view.context, view.context.getDimensionPixelSize(R.dimen.border_radius), 0))
				else -> glideSetting.transforms(CenterCrop())
			}
		}
		var refChild: StorageReference? = null
		when (data) {
			is Car -> refChild = ref.child("cars")
			is Student -> refChild = ref.child("students")
			is Driver -> refChild = ref.child("drivers")
			is Parent -> refChild = ref.child("parents")
		}

		if (data.getKeyOrId().isBlank()) {
			glide.load(placeholder).apply(glideSetting).into(view)
		} else {
			refChild?.let {
				glide.load(it.child(data.getKeyOrId())).apply(glideSetting).into(view)
			}
		}
	}

	@JvmStatic
	@BindingAdapter("fill_data")
	fun fillData(view: PhotoGridView, data: List<Student>) {
		val user = IVan.getUser(view.context)
		var isDriver = false
		var parent: Parent? = null
		user.fold {
			onParent {
				isDriver = false
				parent = it
			}
			onDriver { isDriver = true }
		}
		val result = mutableListOf<Student>()
		data.forEach {
			if (isDriver || it.parent == parent?.getKeyOrId()) {
				result.add(it)
			}
		}
		view.fillData(result)
	}

	@JvmStatic
	@BindingAdapter(value = ["distance_from", "parent_location"], requireAll = true)
	fun fillDistance(view: TextViewRichDrawable, data: MobilityStatus?, parentLocation: Location?) {
		if (data == null) {
			view.text = formatDistance(view.context, null)
		} else {
			val distance = distanceBetween(Location(data.lat, data.lng), parentLocation)
			view.text = formatDistance(view.context, distance)
		}
	}

	@JvmStatic
	@BindingAdapter(value = ["estimate_time", "parent_location"], requireAll = true)
	fun fillEstimateTime(view: TextViewRichDrawable, data: MobilityStatus?, parentLocation: Location?) {
		if (data == null) {
			view.text = formatTime(view.context, null)
		} else {
			estimateTime(view.context, Location(data.lat, data.lng), parentLocation, { direction: Direction?, rawBody ->
				direction?.let {
					Timber.d(rawBody)
					val time = it.routeList[0].legList.fold(0) { acc: Int, leg: Leg? ->
						when (leg) {
							null -> acc
							else -> acc + leg.duration.value.toInt()
						}
					}
					view.text = formatTime(view.context, time)
				}
			})
		}
	}
}