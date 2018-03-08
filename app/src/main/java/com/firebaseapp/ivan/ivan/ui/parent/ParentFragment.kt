package com.firebaseapp.ivan.ivan.ui.parent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.ui.INotifyDataSetChanged
import com.firebaseapp.ivan.ivan.ui.parent.viewholder.ParentContactViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.parent.viewholder.ParentHomeLocationViewHolderFactory
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TYPE_1
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import timber.log.Timber

/**
 * @author phompang on 20/2/2018 AD.
 */
class ParentFragment : Fragment(), INotifyDataSetChanged {
	private val adapter = MultipleViewAdapter<Parent>(2)
	private var parent: Parent? = null

	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}

	companion object {
		val TAG: String = ParentFragment::class.java.simpleName
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
		adapter.registerViewHolderFactory(TYPE_0, ParentContactViewHolderFactory())
		adapter.registerViewHolderFactory(TYPE_1, ParentHomeLocationViewHolderFactory())

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	fun setParent(parent: Parent) {
		this.parent = parent
	}

	override fun notifyDataSetChanged() {
		viewFlipperProgressBarOwn.hideProgressBar()

		Timber.d("${this.parent}")
		this.parent?.let {
			adapter.clear()
			adapter.add(it, TYPE_0)
			adapter.add(it, TYPE_1)
			adapter.notifyDataSetChanged()
		}
	}
}