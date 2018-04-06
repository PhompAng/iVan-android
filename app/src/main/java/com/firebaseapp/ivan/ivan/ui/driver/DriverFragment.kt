package com.firebaseapp.ivan.ivan.ui.driver

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.ui.driver.viewholder.DriverCarViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.driver.viewholder.DriverContactViewHolderFactory
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TYPE_1
import kotlinx.android.synthetic.main.fragment_recyclerview.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class DriverFragment : Fragment(), Injectable {

	private val adapter = MultipleViewAdapter(2)

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}
	private val car by lazy {
		IVan.getCar(context!!)
	}

	companion object {
		val TAG: String = DriverFragment::class.java.simpleName
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_recyclerview)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()

		setUpRecyclerView()
		setUpData()
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, DriverContactViewHolderFactory())
		adapter.registerViewHolderFactory(TYPE_1, DriverCarViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	private fun setUpData() {
		viewFlipperProgressBarOwn.hideProgressBar()
		adapter.add(car, TYPE_0)
		adapter.add(car, TYPE_1)
		adapter.notifyDataSetChanged()
	}
}