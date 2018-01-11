package com.firebaseapp.ivan.ivan.view

import android.widget.ViewFlipper

/**
 * @author phompang on 11/1/2018 AD.
 */
class ViewFlipperProgressBarOwn(var viewFlipper: ViewFlipper, var reverse: Boolean = false) : ProgressBarOwner {
	
	override fun showProgressBar() {
		this.viewFlipper.displayedChild = if (reverse) 1 else 0
	}

	override fun hideProgressBar() {
		this.viewFlipper.displayedChild = if (reverse) 0 else 1
	}
}