package com.firebaseapp.ivan.ivan.ui.students.viewholder

import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.BR
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.databinding.ViewItemStudentThumbnailBinding
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.ui.student.StudentActivity
import com.firebaseapp.ivan.util.inflateBinding
import com.wongnai.android.DataBindingItemViewHolder
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.ViewHolderFactory
import org.jetbrains.anko.startActivity

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentThumbnailViewHolderFactory : ViewHolderFactory<Student> {
	override fun create(parent: ViewGroup?): ItemViewHolder<Student> {
		return StudentThumbnailViewHolder(parent!!.inflateBinding(R.layout.view_item_student_thumbnail))
	}

	inner class StudentThumbnailViewHolder(private val studentThumbnailBinding: ViewItemStudentThumbnailBinding) : DataBindingItemViewHolder<Student>(studentThumbnailBinding) {

		override fun fillData(data: Student, position: Int) {
			studentThumbnailBinding.setVariable(BR.student, data)
			studentThumbnailBinding.executePendingBindings()
			itemView.setOnClickListener {
				getContext().startActivity<StudentActivity>(StudentActivity.EXTRA_STUDENT_UID to data.getKeyOrId())
			}
		}
	}
}