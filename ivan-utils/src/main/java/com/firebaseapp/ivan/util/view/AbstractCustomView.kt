package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * @author phompang on 21/1/2018 AD.
 */
abstract class AbstractCustomView<T> @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

	protected var data: T? = null
	protected var isHideOnNull = true
	private var listener: OnTypeClickListener<T>? = null

	init {
		this.extractAttribute(attrs, defStyleAttr, defStyleRes)
		init()
	}

	open fun extractAttribute(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {}

	private fun init() {
		inflate(context, getContentLayout(), this)

		if (isInEditMode) {
			fillDataInEditMode()
			return
		}
		setOnClickListener {
			listener?.onClick(it, data)
		}
	}

	@LayoutRes
	internal abstract fun getContentLayout(): Int

	open fun fillDataInEditMode() {}

	open fun fillDataNullable(d: T?) {}

	open fun fillDataNonNull(d: T) {}

	fun fillData(d: T?) {
		try {
			this.data = d
			fillDataNullable(d)
			if (d != null) {
				visibility = View.VISIBLE
				fillDataNonNull(d)
			} else {
				if (isHideOnNull) {
					visibility = View.GONE
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
			visibility = View.GONE
		}
	}

	fun setOnClickListener(listener: OnTypeClickListener<T>) {
		this.listener = listener
	}

	public interface OnTypeClickListener<in T> {
		fun onClick(view: View, data: T?)
	}

}

fun <T> OnTypeClickListener(l: (View, T?) -> Unit): AbstractCustomView.OnTypeClickListener<T> {
	return object : AbstractCustomView.OnTypeClickListener<T> {
		override fun onClick(view: View, data: T?) {
			l(view, data)
		}
	}
}
