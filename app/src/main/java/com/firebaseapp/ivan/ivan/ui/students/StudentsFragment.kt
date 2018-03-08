package com.firebaseapp.ivan.ivan.ui.students

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.ui.carmap.CarViewModel
import com.firebaseapp.ivan.ivan.ui.students.viewholder.StudentThumbnailViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.*
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import kotlinx.android.synthetic.main.fragment_students.*

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentsFragment : Fragment(), Injectable {
	private lateinit var viewModel: CarViewModel

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}
	private val car by lazy {
		IVan.getCar(context!!)
	}
	private val parent by lazy {
		IVan.getUser(context!!)
	}
	private var adapter = MultipleViewAdapter<Student>(1)

	companion object {
		val TAG: String = StudentsFragment::class.java.simpleName

		fun newInstance(): StudentsFragment = StudentsFragment()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_students)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()

		setUpRecyclerView()
		setUpViewModel()
		setUpData()
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, StudentThumbnailViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
		recyclerView.addItemDecoration(LayoutMarginDecoration(3, convertToPx(context!!, 16)))
	}

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(CarViewModel::class.java)
		viewModel.setCar(car)
		viewModel.getSchool().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			it ?: return@observe
			schoolNameTextView.text = it.name.th
		}
	}

	private fun setUpData() {
		val isDriver = false
		car.students.forEach {
			if (isDriver || it.parent == parent.getKeyOrId())
			adapter.add(it, TYPE_0)
		}
		carPlateNumberTextView.text = car.plateNumber
		provinceTextView.text = car.province
	}
}