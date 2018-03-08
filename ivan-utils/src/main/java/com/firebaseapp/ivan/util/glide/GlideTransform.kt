package com.firebaseapp.ivan.util.glide

import android.support.annotation.IntDef

/**
 * @author phompang on 26/2/2018 AD.
 */
class GlideTransformClass {
	companion object {
		const val NONE = 0L
		const val CIRCLE = 1L
		const val ROUND_CORNER = 2L
	}

	@IntDef(CIRCLE, ROUND_CORNER)
	@Retention(AnnotationRetention.SOURCE)
	annotation class GlideTransform
}
