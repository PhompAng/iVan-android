package com.firebaseapp.ivan.util.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * @author phompang on 21/1/2018 AD.
 */
abstract class AbstractCustomView<T> @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
	protected var data: T? = null
	protected var isHideOnNull = true
	var listener: OnTypeClickListener<T>? = null

	init {
		extractAttribute(attrs, defStyleAttr)
		init()
	}

	open fun extractAttribute(attrs: AttributeSet?, defStyleAttr: Int) {}

	protected fun init() {
		View.inflate(context, getContentLayout(), this)

		if (isInEditMode) {
			fillDataInEditMode()
			return
		}
		setOnClickListener {
			listener?.onClick(it, this.data)
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

	public interface OnTypeClickListener<in T> {
		fun onClick(view: View, data: T?)
	}
}
