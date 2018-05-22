package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.StyleRes
import android.util.AttributeSet
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.glide.GlideTransformClass
import com.firebaseapp.ivan.util.R
import kotlinx.android.synthetic.main.view_item_mini_student.view.*

/**
 * @author phompang on 21/1/2018 AD.
 */
class MiniStudentView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : AbstractCustomView<Student>(context, attrs, defStyleAttr, defStyleRes) {

	override fun fillDataNonNull(d: Student) {
		DataBindingUtils.loadFromFirebaseStorage(userThumbnailImageView, d, context.getDrawable(R.drawable.portrait_placeholder), GlideTransformClass.CIRCLE)
		nameImageView.text = d.fullName()
	}

	override fun getContentLayout(): Int = R.layout.view_item_mini_student

}