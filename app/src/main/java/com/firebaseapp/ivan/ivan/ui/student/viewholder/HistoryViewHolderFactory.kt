package com.firebaseapp.ivan.ivan.ui.student.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.delegate.CarHistory
import com.firebaseapp.ivan.ivan.delegate.DelegateStudent
import com.firebaseapp.ivan.util.inflate
import com.wongnai.android.*
import kotlinx.android.synthetic.main.view_item_car_history.view.*

/**
 * @author phompang on 1/5/2018 AD.
 */
class HistoryViewHolderFactory : ViewHolderFactory<DelegateStudent> {
	override fun create(parent: ViewGroup?): ItemViewHolder<*> {
		return HistoryViewHolder(parent!!.inflate(R.layout.view_item_car_history))
	}

	private inner class HistoryViewHolder(container: View) : NormalViewHolder<DelegateStudent>(container) {
		private val adapter = MultipleViewAdapter(1)

		init {
			adapter.registerViewHolderFactory(TYPE_0, HistoryItemViewHolderFactory())
			itemView.carHistoryRecyclerView.adapter = adapter
			itemView.carHistoryRecyclerView.layoutManager = LinearLayoutManager(getContext())
		}

		override fun fillData(data: DelegateStudent, position: Int) {
			adapter.clear()
			data.student.carHistory.toSortedMap().entries.forEach {
				val v = it.value.toList().sortedBy { (_, value) -> value.timestamp }
				val carHistory = CarHistory(it.key, v.getOrNull(0)?.second?.timestamp, v.getOrNull(1)?.second?.timestamp)
				adapter.add(carHistory, TYPE_0)
			}
		}
	}
}