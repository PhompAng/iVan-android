package com.firebaseapp.ivan.ivan.ui.student.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.delegate.DelegateStudent
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.ui.parent.ParentActivity
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.glide.GlideTransformClass
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_student_parent.view.*
import org.jetbrains.anko.startActivity

/**
 * @author phompang on 20/2/2018 AD.
 */
class StudentParentViewHolderFactory : ViewHolderFactory<DelegateStudent> {
	override fun create(parent: ViewGroup?): ItemViewHolder<DelegateStudent> {
		return StudentParentViewHolder(parent!!.inflate(R.layout.view_item_student_parent))
	}

	inner class StudentParentViewHolder(container: View) : NormalViewHolder<DelegateStudent>(container) {
		override fun fillData(data: DelegateStudent, position: Int) {
			val parent = data.parent
			DataBindingUtils.loadFromFirebaseStorage(itemView.userThumbnailImageView, parent, getContext().getDrawable(R.drawable.portrait_placeholder), GlideTransformClass.CIRCLE)
			itemView.userNameTextView.text = parent.fullName()

			itemView.setOnClickListener {
				getContext().startActivity<ParentActivity>(ParentActivity.EXTRA_PARENT_ID to parent.getKeyOrId())
			}
		}
	}
}