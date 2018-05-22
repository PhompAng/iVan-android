package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.firebaseapp.ivan.ivan.model.FirebaseModel
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.ROUND_CORNER
import com.firebaseapp.ivan.util.R
import kotlinx.android.synthetic.main.view_stub_photo_grid.view.*

/**
 * @author phompang on 26/2/2018 AD.
 */
class PhotoGridView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : AbstractCustomView<List<FirebaseModel>>(context, attrs, defStyleAttr, defStyleRes) {

	private lateinit var image1: ImageView
	private lateinit var image2: ImageView
	private lateinit var image3: ImageView
	private lateinit var image4: ImageView
	private lateinit var image5: ImageView
	private lateinit var image1Description: TextView
	private lateinit var image2Description: TextView
	private lateinit var image3Description: TextView
	private lateinit var image4Description: TextView
	private lateinit var image5Description: TextView

	override fun getContentLayout(): Int = R.layout.view_stub_photo_grid

	override fun fillDataInEditMode() {
		image1ViewStub.visibility = View.GONE
		image2ViewStub.visibility = View.GONE
		image3ViewStub.visibility = View.GONE
		image4ViewStub.visibility = View.GONE
		image5ViewStub.visibility = View.GONE
	}

	override fun fillDataNonNull(d: List<FirebaseModel>) {
		when (d.size) {
			0 -> visibility = View.GONE
			1 -> {
				inflateView(d, image1ViewStub)
				DataBindingUtils.loadFromFirebaseStorage(image1, d[0], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image1Description, d[0])
			}
			2 -> {
				inflateView(d, image2ViewStub)
				DataBindingUtils.loadFromFirebaseStorage(image1, d[0], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image1Description, d[0])
				DataBindingUtils.loadFromFirebaseStorage(image2, d[1], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image2Description, d[1])
			}
			3 -> {
				inflateView(d, image3ViewStub)
				DataBindingUtils.loadFromFirebaseStorage(image1, d[0], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image1Description, d[0])
				DataBindingUtils.loadFromFirebaseStorage(image2, d[1], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image2Description, d[1])
				DataBindingUtils.loadFromFirebaseStorage(image3, d[2], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image3Description, d[2])
			}
			4 -> {
				inflateView(d, image4ViewStub)
				DataBindingUtils.loadFromFirebaseStorage(image1, d[0], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image1Description, d[0])
				DataBindingUtils.loadFromFirebaseStorage(image2, d[1], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image2Description, d[1])
				DataBindingUtils.loadFromFirebaseStorage(image3, d[2], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image3Description, d[2])
				DataBindingUtils.loadFromFirebaseStorage(image4, d[3], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image4Description, d[3])
			}
			else -> {
				inflateView(d, image5ViewStub)
				DataBindingUtils.loadFromFirebaseStorage(image1, d[0], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image1Description, d[0])
				DataBindingUtils.loadFromFirebaseStorage(image2, d[1], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image2Description, d[1])
				DataBindingUtils.loadFromFirebaseStorage(image3, d[2], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image3Description, d[2])
				DataBindingUtils.loadFromFirebaseStorage(image4, d[3], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image4Description, d[3])
				DataBindingUtils.loadFromFirebaseStorage(image5, d[4], context.getDrawable(R.drawable.portrait_placeholder), ROUND_CORNER)
				fillDescription(image5Description, d[4])
			}
		}
	}

	private fun fillDescription(view: TextView, model: FirebaseModel) {
		when (model) {
			is Student -> view.text = model.fullName()
		}
	}

	private fun inflateView(d: List<FirebaseModel>, viewStub: ViewStub?) {
		if (viewStub == null) {
			return
		}
		val v = viewStub.inflate()
		when (d.size) {
			1 -> {
				image1 = v.findViewById(R.id.imageView1)
				image1Description = v.findViewById(R.id.image1Description)
			}
			2 -> {
				image1 = v.findViewById(R.id.imageView1)
				image1Description = v.findViewById(R.id.image1Description)
				image2 = v.findViewById(R.id.imageView2)
				image2Description = v.findViewById(R.id.image2Description)
			}
			3 -> {
				image1 = v.findViewById(R.id.imageView1)
				image1Description = v.findViewById(R.id.image1Description)
				image2 = v.findViewById(R.id.imageView2)
				image2Description = v.findViewById(R.id.image2Description)
				image3 = v.findViewById(R.id.imageView3)
				image3Description = v.findViewById(R.id.image3Description)
			}
			4 -> {
				image1 = v.findViewById(R.id.imageView1)
				image1Description = v.findViewById(R.id.image1Description)
				image2 = v.findViewById(R.id.imageView2)
				image2Description = v.findViewById(R.id.image2Description)
				image3 = v.findViewById(R.id.imageView3)
				image3Description = v.findViewById(R.id.image3Description)
				image4 = v.findViewById(R.id.imageView4)
				image4Description = v.findViewById(R.id.image4Description)
			}
			else -> {
				image1 = v.findViewById(R.id.imageView1)
				image1Description = v.findViewById(R.id.image1Description)
				image2 = v.findViewById(R.id.imageView2)
				image2Description = v.findViewById(R.id.image2Description)
				image3 = v.findViewById(R.id.imageView3)
				image3Description = v.findViewById(R.id.image3Description)
				image4 = v.findViewById(R.id.imageView4)
				image4Description = v.findViewById(R.id.image4Description)
				image5 = v.findViewById(R.id.imageView5)
				image5Description = v.findViewById(R.id.image5Description)
			}
		}
	}
}