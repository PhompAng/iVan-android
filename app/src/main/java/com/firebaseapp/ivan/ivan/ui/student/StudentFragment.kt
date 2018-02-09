package com.firebaseapp.ivan.ivan.ui.student

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.ui.student.viewholder.HomeLocationViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import kotlinx.android.synthetic.main.fragment_student.*
import kotlinx.android.synthetic.main.layout_student_info.*

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentFragment : Fragment(), Injectable {

	private lateinit var viewModel: StudentViewModel
	private val adapter = MultipleViewAdapter<Student>(1)
	private var studentUid = ""

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = StudentFragment::class.java.simpleName

		fun newInstance(studentUid: String): StudentFragment {
			val fragment = StudentFragment()
			fragment.arguments = Bundle().apply {
				putString(StudentActivity.EXTRA_STUDENT_UID, studentUid)
			}
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_student)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()
		when (savedInstanceState) {
			null -> extractExtras(arguments)
			else -> extractExtras(savedInstanceState)
		}

		setUpRecyclerView()
		setUpViewModel()
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, HomeLocationViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(StudentViewModel::class.java)
		viewModel.setStudentUid(studentUid)
		viewModel.getStudent().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			it ?: return@observe
			setUpData(it)
			adapter.add(it, TYPE_0)
			adapter.notifyDataSetChanged()
		}
		viewModel.getSchool().observe(this) {
			it ?: return@observe
			schoolNameTextView.text = it.name.th
		}
	}

	private fun setUpData(student: Student) {
		DataBindingUtils.loadFromFirebaseStorage(
				userThumbnailImageView,
				student,
				context?.getDrawable(R.drawable.portrait_placeholder),
				true
		)
		nameTextView.text = student.getFullName()
		userIdTextView.text = context!!.getString(R.string.student_id, student.no)
	}

	private fun extractExtras(bundle: Bundle?) {
		bundle?.let {
			studentUid = it.getString(StudentActivity.EXTRA_STUDENT_UID, "")
		}
	}
}