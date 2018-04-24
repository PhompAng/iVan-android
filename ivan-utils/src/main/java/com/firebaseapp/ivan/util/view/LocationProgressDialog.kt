package com.firebaseapp.ivan.util.view

import android.app.ProgressDialog
import android.content.Context
import com.firebaseapp.ivan.util.R

/**
 * @author phompang on 19/4/2018 AD.
 */
class LocationProgressDialog(context: Context) : ProgressDialog(context) {
	init {
		setMessage(context.getString(R.string.msg_waiting_current_location))
		isIndeterminate = true
		setCancelable(false)
	}
}

