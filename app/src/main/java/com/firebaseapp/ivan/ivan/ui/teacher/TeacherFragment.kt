package com.firebaseapp.ivan.ivan.ui.teacher

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.ivan.ui.INotifyDataSetChanged
import com.firebaseapp.ivan.ivan.ui.teacher.viewholder.TeacherCarViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.teacher.viewholder.TeacherContactViewHolderFactory
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TYPE_1
import kotlinx.android.synthetic.main.fragment_recyclerview.*

/**
 * @author phompang on 6/4/2018 AD.
 */
class TeacherFragment : Fragment(), Injectable, INotifyDataSetChanged {
	private val adapter = MultipleViewAdapter(2)
	private var teacher: Teacher? = null

	private val car by lazy {
		IVan.getCar(context!!)
	}
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = TeacherFragment::class.java.simpleName
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_recyclerview)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()

		setUpRecyclerView()
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, TeacherContactViewHolderFactory())
		adapter.registerViewHolderFactory(TYPE_1, TeacherCarViewHolderFactory())
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	fun setTeacher(teacher: Teacher) {
		this.teacher = teacher
	}

	override fun notifyDataSetChanged() {
		viewFlipperProgressBarOwn.hideProgressBar()
		this.teacher?.let {
			adapter.clear()
			adapter.add(it, TYPE_0)
			adapter.add(car, TYPE_1)
		}
	}

}