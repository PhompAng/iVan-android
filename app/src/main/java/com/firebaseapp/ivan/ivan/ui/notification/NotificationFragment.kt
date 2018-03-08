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
import com.firebaseapp.ivan.ivan.model.Notification
import com.firebaseapp.ivan.ivan.ui.notification.viewholder.NotificationViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.IVan
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
	private val user by lazy {
		IVan.getUser(context!!)
	}
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = NotificationFragment::class.java.simpleName

		fun newInstance(): NotificationFragment = NotificationFragment()
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
		}
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, NotificationViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context).apply {
			reverseLayout = true
			stackFromEnd = true
		}
		recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
		recyclerView.setHasFixedSize(true)
	}

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(NotificationViewModel::class.java)
		viewModel.setUserId(user.getKeyOrId())
		viewModel.getNotifications().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			it ?: return@observe
			adapter.clear()
			adapter.addAll(it, TYPE_0)
		}
	}
}