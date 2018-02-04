package com.firebaseapp.ivan.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.request.RequestOptions
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.model.FirebaseModel
import com.firebaseapp.ivan.ivan.model.Student
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
	@BindingAdapter("storage")
	fun loadFromFirebaseStorage(view: ImageView, data: FirebaseModel) {
		val ref = FirebaseStorage.getInstance().reference
		val glide = GlideApp.with(view.context)
		val glideSetting = RequestOptions().placeholder(R.drawable.ic_error_outline_black_24dp).centerCrop()
		var refChild: StorageReference? = null
		when (data) {
			is Car -> refChild = ref.child("cars").child(data.getKeyOrId())
			is Student -> refChild = ref.child("students").child(data.getKeyOrId())
		}

		refChild?.let {
			glide.load(it).apply(glideSetting).into(view)
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