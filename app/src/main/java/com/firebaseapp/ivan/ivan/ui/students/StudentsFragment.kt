package com.firebaseapp.ivan.ivan.ui.students

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.ui.carmap.CarMapViewModel
import com.firebaseapp.ivan.ivan.ui.students.viewholder.StudentThumbnailViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import kotlinx.android.synthetic.main.fragment_students.*
import javax.inject.Inject

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentsFragment : Fragment(), Injectable {
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private lateinit var viewModel: CarMapViewModel

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}
	private val car by lazy {
		IVan.getCar(context!!)
	}
	private val user by lazy {
		IVan.getUser(context!!)
	}
	private var adapter = MultipleViewAdapter(1)

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
		viewModel = activity!!.obtainViewModel(viewModelFactory, CarMapViewModel::class.java)
		viewModel.setCar(car)
		viewModel.getSchool().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			it ?: return@observe
			schoolNameTextView.text = it.name.th
		}
	}

	private fun setUpData() {
		var isDriverOrTeacher = false
		var parent: Parent? = null
		user.fold {
			onParent {
				isDriverOrTeacher = false
				parent = it
			}
			onDriver { isDriverOrTeacher = true }
			onTeacher { isDriverOrTeacher = true }
		}
		car.students.forEach {
			if (isDriverOrTeacher || it.parent == parent?.getKeyOrId()) {
				adapter.add(it, TYPE_0)
			}
		}
		carPlateNumberTextView.text = car.plateNumber
		provinceTextView.text = car.province
	}
}