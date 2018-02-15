package com.firebaseapp.ivan.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.request.RequestOptions
import com.firebaseapp.ivan.ivan.model.*
import com.firebaseapp.ivan.util.view.MiniStudentView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


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
	@BindingAdapter(value = ["storage", "placeholder", "circle"], requireAll = false)
	fun loadFromFirebaseStorage(view: ImageView, data: FirebaseModel, placeholder: Drawable?, isCircle: Boolean?) {
		val ref = FirebaseStorage.getInstance().reference
		val glide = GlideApp.with(view.context)
		var glideSetting: RequestOptions = if (placeholder == null) RequestOptions().placeholder(R.drawable.ic_photo_grey500_24dp) else RequestOptions().placeholder(placeholder)
		glideSetting = glideSetting.centerCrop()
		if (isCircle != null && isCircle) {
			glideSetting = glideSetting.circleCrop()
		}
		var refChild: StorageReference? = null
		when (data) {
			is Car -> refChild = ref.child("cars")
			is Student -> refChild = ref.child("students")
			is Driver -> refChild = ref.child("drivers")
			is Parent -> refChild = ref.child("parents")
		}

		refChild?.let {
			glide.load(it.child(data.getKeyOrId())).apply(glideSetting).into(view)
		}
	}

	@JvmStatic
	@BindingAdapter("studentList")
	fun fillMiniStudentList(view: LinearLayout, data: List<Student>) {
		view.removeAllViews()
		data.forEach {
			if (it.parent == IVan.getUser(view.context).key) {
				val studentView = MiniStudentView(view.context)
				studentView.fillData(it)
				view.addView(studentView)
			}
		}
	}
}