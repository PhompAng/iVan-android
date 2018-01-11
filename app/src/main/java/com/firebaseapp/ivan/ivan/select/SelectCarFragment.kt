package com.firebaseapp.ivan.ivan.select


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.EXTRA_UID

import com.firebaseapp.ivan.ivan.R


class SelectCarFragment : Fragment() {
	private lateinit var uid: String

	companion object {
		val TAG: String = SelectCarFragment::class.java.simpleName

		fun newInstance(uid: String): SelectCarFragment = SelectCarFragment().apply {
			arguments = Bundle().apply {
				putString(EXTRA_UID, uid)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_select_car, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

	}
}