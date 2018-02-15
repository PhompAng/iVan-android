package com.firebaseapp.ivan.ivan.ui.notification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.api.Notification
import com.firebaseapp.ivan.ivan.ui.notification.viewholder.NotificationViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import kotlinx.android.synthetic.main.fragment_recyclerview.*

/**
 * @author phompang on 13/2/2018 AD.
 */
class NotificationFragment : Fragment(), Injectable {

	private lateinit var viewModel: NotificationViewModel
	private val adapter = MultipleViewAdapter<Notification>(1)
	private var schoolId = ""
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = NotificationFragment::class.java.simpleName
		const val EXTRA_SCHOOL_ID = "extra-school-id"

		fun newInstance(schoolId: String): NotificationFragment {
			val fragment = NotificationFragment()
			fragment.arguments = Bundle().apply {
				putString(EXTRA_SCHOOL_ID, schoolId)
			}
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_recyclerview)
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

	private fun extractExtras(bundle: Bundle?) {
		bundle?.let {
			schoolId = it.getString(EXTRA_SCHOOL_ID, "")
		}
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, NotificationViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
		recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
	}

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(NotificationViewModel::class.java)
		viewModel.setSchoolId(schoolId)
		viewModel.getNotifications().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			it ?: return@observe
			adapter.clear()
			adapter.addAll(it, TYPE_0)
		}
	}
}