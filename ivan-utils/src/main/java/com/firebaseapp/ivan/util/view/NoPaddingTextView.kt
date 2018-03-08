package com.firebaseapp.ivan.util.view

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView

/**
 * @author phompang on 25/2/2018 AD.
 */

class NoPaddingTextView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
	private var textHeight = -1


	override fun onDraw(canvas: Canvas) {
		var offset = 0f
		if (textHeight > -1) {
			offset = (textHeight - lineHeight).toFloat()
		}
		canvas.translate(0f, offset) //or +offset to moving it to top
		super.onDraw(canvas)
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		var heightMeasureSpec = heightMeasureSpec
		val mode = MeasureSpec.getMode(heightMeasureSpec)
		if (mode != MeasureSpec.EXACTLY) {
			val measureHeight = measureHeight(text.toString(), widthMeasureSpec)
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY)
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
	}

	private fun measureHeight(text: String, widthMeasureSpec: Int): Int {
		if (textHeight > -1) {
			return textHeight
		}
		val textSize = textSize

		val textView = TextView(context)
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
		textView.text = text
		textView.typeface = typeface
		textView.includeFontPadding = false
		textView.measure(widthMeasureSpec, 0)
		textHeight = textView.measuredHeight
		return textHeight
	}
}