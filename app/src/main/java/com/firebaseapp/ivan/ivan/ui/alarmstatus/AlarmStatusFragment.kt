package com.firebaseapp.ivan.ivan.ui.alarmstatus

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.AlarmStatus
import com.firebaseapp.ivan.ivan.ui.alarmstatus.holder.AlarmStatusViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.alarmstatus.holder.StaticMapViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TYPE_1
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author phompang on 13/4/2018 AD.
 */
class AlarmStatusFragment : Fragment(), Injectable {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private lateinit var uid: String
	private val adapter = MultipleViewAdapter(1)
	private lateinit var viewModel: AlarmStatusViewModel

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = AlarmStatusFragment::class.java.simpleName

		fun newInstance(uid: String): AlarmStatusFragment {
			return AlarmStatusFragment().apply {
				arguments = Bundle().apply {
					putString(AlarmStatusActivity.EXTRA_UID, uid)
				}
			}
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

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(viewModelFactory, AlarmStatusViewModel::class.java)
		viewModel.setAlarmStatusUid(uid)
		viewModel.getAlarmStatus().observe(this) {
			viewFlipperProgressBarOwn.hideProgressBar()
			adapter.clear()
			Timber.d("$it")
			it ?: return@observe
			adapter.add(it, TYPE_0)
			adapter.add(it, TYPE_1)
		}
		viewModel.getReportFalseResult().observe(this) {
			it ?: return@observe
			if (it) {
				adapter.notifyDataSetChanged()
				val dialog = AlertDialog.Builder(context!!)
						.setTitle(R.string.complete)
						.setPositiveButton(R.string.action_close) { dialog, _ ->
							viewModel.setReportFalseResult(false)
							dialog.dismiss()
						}
				dialog.show()
			}
		}
	}

	private fun extractExtras(bundle: Bundle?) {
		bundle?.let {
			uid = it.getString(AlarmStatusActivity.EXTRA_UID)
		}
	}

	private fun setUpRecyclerView() {
		adapter.registerViewHolderFactory(TYPE_0, StaticMapViewHolderFactory())
		adapter.registerViewHolderFactory(TYPE_1, AlarmStatusViewHolderFactory(ReportFalseAlarmListener()))
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putString(AlarmStatusActivity.EXTRA_UID, uid)
	}

	private inner class ReportFalseAlarmListener : AlarmStatusViewHolderFactory.OnReportFalseAlarmListener {
		override fun onReport(data: AlarmStatus) {
			viewModel.reportFalseAlarm(data.uid)
		}
	}
}