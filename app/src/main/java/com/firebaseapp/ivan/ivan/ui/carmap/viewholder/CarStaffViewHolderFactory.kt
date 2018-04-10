package com.firebaseapp.ivan.ivan.ui.carmap.viewholder

import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Driver
import com.firebaseapp.ivan.ivan.model.FirebaseModel
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.ui.driver.DriverActivity
import com.firebaseapp.ivan.ivan.ui.teacher.TeacherActivity
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.CIRCLE
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.ItemViewHolder
import com.wongnai.android.NormalViewHolder
import com.wongnai.android.ViewHolderFactory
import kotlinx.android.synthetic.main.view_item_car_staff.view.*
import org.jetbrains.anko.startActivity

/**
 * @author phompang on 7/4/2018 AD.
 */
class CarStaffViewHolderFactory<T : FirebaseModel> : ViewHolderFactory<T> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return CarStaffViewHolder(parent!!.inflate(R.layout.view_item_car_staff))
	}

	private inner class CarStaffViewHolder(container: View) : NormalViewHolder<T>(container) {
		override fun fillData(data: T, position: Int) {
			DataBindingUtils.loadFromFirebaseStorage(itemView.userImageView, data, getContext().getDrawable(R.drawable.portrait_placeholder), CIRCLE)
			when (data) {
				is Driver -> {
					itemView.nameTextView.text = data.fullName()
					itemView.roleTextView.text = getContext().getString(R.string.driver)
					itemView.setOnClickListener {
						getContext().startActivity<DriverActivity>()
					}
				}
				is Teacher -> {
					itemView.nameTextView.text = data.fullName()
					itemView.roleTextView.text = getContext().getString(R.string.teacher)
					itemView.setOnClickListener {
						getContext().startActivity<TeacherActivity>(TeacherActivity.EXTRA_TEACHER_ID to data.getKeyOrId())
					}
				}
			}
		}
	}
}