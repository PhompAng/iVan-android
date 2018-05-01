package com.firebaseapp.ivan.ivan.ui.alarmstatus.holder

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.AlarmStatus
import com.firebaseapp.ivan.util.getRelativeTime
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.*
import kotlinx.android.synthetic.main.view_item_alarm_status.view.*

/**
 * @author phompang on 14/4/2018 AD.
 */
class AlarmStatusViewHolderFactory(
		private val onReportFalseAlarmListener: OnReportFalseAlarmListener,
		private val onConfirmStudentSecuredListener: OnConfirmStudentSecuredListener
) : ViewHolderFactory<AlarmStatus> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return AlarmStatusViewHolder(parent!!.inflate(R.layout.view_item_alarm_status))
	}

	private inner class AlarmStatusViewHolder(container: View) : NormalViewHolder<AlarmStatus>(container) {
		private val adapter = MultipleViewAdapter(1)
		private lateinit var data: AlarmStatus

		private val reportFalseDialog by lazy {
			AlertDialog.Builder(getContext())
					.setTitle(R.string.report_false_alarm_question)
					.setNegativeButton(R.string.action_cancel) { dialog, _ ->
						dialog.dismiss()
					}
					.setPositiveButton(R.string.action_report) { dialog, _ ->
						onReportFalseAlarmListener.onReport(data)
						dialog.dismiss()
					}
		}

		private val confirmDialog by lazy {
			AlertDialog.Builder(getContext())
					.setTitle(R.string.confirm_secured_question)
					.setNegativeButton(R.string.action_cancel) { dialog, _ ->
						dialog.dismiss()
					}
					.setPositiveButton(R.string.action_confirm) { dialog, _ ->
						onConfirmStudentSecuredListener.onConfirm(data)
						dialog.dismiss()
					}
		}

		init {
			adapter.registerViewHolderFactory(TYPE_0, AlarmStatusDataViewHolderFactory())
			itemView.recyclerView.adapter = adapter
			itemView.recyclerView.layoutManager = LinearLayoutManager(getContext())
			itemView.reportFalseButton.setOnClickListener {
				reportFalseDialog.show()
			}
			itemView.confirmSecureButton.setOnClickListener {
				confirmDialog.show()
			}
		}

		override fun fillData(data: AlarmStatus, position: Int) {
			this.data = data
			itemView.detection.text = getContext().getString(R.string.alarm_status_detection, data.detection)
			itemView.timestampTextView.text = data.timestamp.getRelativeTime(getContext())
			adapter.clear()
			adapter.addAll(data.data, TYPE_0)

			if (data.isReportFalse) {
				itemView.falseAlarmView.visibility = View.VISIBLE
				itemView.confirmedView.visibility = View.GONE
				itemView.alarmStatusGroup.background = getContext().getDrawable(R.drawable.fg_disabled)
				itemView.reportFalseButton.visibility = View.GONE
				itemView.confirmSecureButton.visibility = View.GONE
				itemView.confirmHint.visibility = View.GONE
			} else if (data.confirm.timestamp != 0L) {
				itemView.confirmedView.visibility = View.VISIBLE
				itemView.falseAlarmView.visibility = View.GONE
				itemView.alarmStatusGroup.background = getContext().getDrawable(R.drawable.fg_disabled)
				itemView.reportFalseButton.visibility = View.GONE
				itemView.confirmSecureButton.visibility = View.GONE
				itemView.confirmHint.visibility = View.GONE
			}
		}
	}

	interface OnReportFalseAlarmListener {
		fun onReport(data: AlarmStatus)
	}

	interface OnConfirmStudentSecuredListener {
		fun onConfirm(data: AlarmStatus)
	}
}