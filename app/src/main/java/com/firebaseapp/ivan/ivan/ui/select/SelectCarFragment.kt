package com.firebaseapp.ivan.ivan.ui.select


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.util.EXTRA_UID
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.delegate.DelegateCar
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Car
import com.firebaseapp.ivan.ivan.ui.select.viewholder.SelectCarViewHolderFactory
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.google.firebase.messaging.FirebaseMessaging
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TypeItemEventListener
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import timber.log.Timber


class SelectCarFragment : Fragment(), Injectable {
	private lateinit var viewModel: SelectCarViewModel

	private lateinit var selectCarCallBack: SelectCarCallback
	private lateinit var uid: String
	private val parent by lazy {
		IVan.getParent(context!!)
	}
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(viewFlipper)
	}
	private var adapter = MultipleViewAdapter<DelegateCar>(1)

	companion object {
		val TAG: String = SelectCarFragment::class.java.simpleName

		fun newInstance(uid: String): SelectCarFragment = SelectCarFragment().apply {
			arguments = Bundle().apply {
				putString(EXTRA_UID, uid)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_recyclerview, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()
		viewModel = activity!!.obtainViewModel(SelectCarViewModel::class.java)

		when (savedInstanceState) {
			null -> extractExtra(arguments)
			else -> extractExtra(savedInstanceState)
		}
		adapter.registerViewHolderFactory(TYPE_0, SelectCarViewHolderFactory(OnCarClickListener()))
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(context)
		recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

		viewModel.setUid(this.uid)
		viewModel.getCars().observe(this) { list: List<Car>? ->
			list ?: return@observe
			viewFlipperProgressBarOwn.hideProgressBar()
			adapter.clear()
			list.forEach {
				FirebaseMessaging.getInstance().subscribeToTopic(it.key)
				parent?.let { p ->
					adapter.add(DelegateCar(it, p), TYPE_0)
				}
			}
		}
	}

	private fun extractExtra(bundle: Bundle?) {
		uid = bundle?.getString(EXTRA_UID, "") ?: ""
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putString(EXTRA_UID, uid)
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		try {
			selectCarCallBack = context as SelectCarCallback
		} catch (e: ClassCastException) {
			throw ClassCastException("${context.toString()} must implement SelectCarCallback")
		}
	}

	inner class OnCarClickListener : TypeItemEventListener<DelegateCar> {
		override fun onItemClick(view: View, item: DelegateCar, position: Int) {
			IVan.setCar(context!!, item.car)
			selectCarCallBack.onCarSelect()
		}
	}

	interface SelectCarCallback {
		fun onCarSelect()
	}
}