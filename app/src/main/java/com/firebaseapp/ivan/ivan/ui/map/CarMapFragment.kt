package com.firebaseapp.ivan.ivan.ui.map

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import com.firebaseapp.ivan.ivan.BR
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.databinding.FragmentCarMapBinding
import com.firebaseapp.ivan.ivan.delegate.DelegateMobilityStatus
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.ui.map.viewholder.MobilityStatusViewHolderFactory
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.github.reline.GoogleMapsBottomSheetBehavior
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import com.wongnai.android.MultipleViewAdapter
import com.wongnai.android.TYPE_0
import com.wongnai.android.TYPE_1
import javax.inject.Inject

/**
 * @author phompang on 21/1/2018 AD.
 */
class CarMapFragment : Fragment(), Injectable, OnMapReadyCallback {
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private lateinit var viewModel: CarMapViewModel
	private lateinit var binding: FragmentCarMapBinding
	private lateinit var mapView: MapView
	private lateinit var mMap: GoogleMap
	private lateinit var bottomSheet: GoogleMapsBottomSheetBehavior<View>
	private lateinit var recyclerView: RecyclerView
	private val adapter = MultipleViewAdapter<DelegateMobilityStatus>(1)

	private val car by lazy {
		IVan.getCar(context!!)
	}
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(binding.viewFlipper)
	}

	companion object {
		val TAG: String = CarMapFragment::class.java.simpleName

		fun newInstance(): CarMapFragment = CarMapFragment()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return DataBindingUtil.inflate<FragmentCarMapBinding>(inflater, R.layout.fragment_car_map, container, false).also {
			binding = it
		}.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		setUpMap(savedInstanceState)
		setUpBottomSheet()
		setUpViewModel()
		setUpAdapter()

		binding.setVariable(BR.car, car)
	}

	private fun setUpMap(savedInstanceState: Bundle?) {
		mapView = binding.map
		mapView.onCreate(savedInstanceState)
		mapView.getMapAsync(this)
	}

	private fun setUpAdapter() {
		adapter.registerViewHolderFactory(TYPE_0, MobilityStatusViewHolderFactory(R.color.colorPrimary, R.color.red500))
		adapter.registerViewHolderFactory(TYPE_1, MobilityStatusViewHolderFactory(R.color.red500, R.color.colorPrimary))
	}

	private fun setUpViewModel() {
		viewModel = ViewModelProviders.of(this, viewModelFactory).get(CarMapViewModel::class.java)
		viewModel.setCar(car)
		viewModel.getMobilityStatus().observe(this) {
			it ?: return@observe
			val latLng = LatLng(it.lat, it.lng)
			mMap.clear()
			mMap.addMarker(MarkerOptions().position(latLng))
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

			adapter.clear()
			adapter.add(DelegateMobilityStatus.getAvgSpeed(context!!, it), TYPE_0)
			adapter.add(DelegateMobilityStatus.getOilLevel(context!!, it), TYPE_1)
		}
		viewModel.getSchool().observe(this) {
			it ?: return@observe
			binding.destinationTextView.text = it.name.th
		}
	}

	private fun setUpBottomSheet() {
		bottomSheet = GoogleMapsBottomSheetBehavior.from(binding.carBottomSheet)
		bottomSheet.state = GoogleMapsBottomSheetBehavior.STATE_COLLAPSED
		bottomSheet.parallax = binding.carImageView
		bottomSheet.setBottomSheetCallback(object : GoogleMapsBottomSheetBehavior.BottomSheetCallback() {
			override fun onStateChanged(bottomSheet: View, newState: Int) {
				if (newState == GoogleMapsBottomSheetBehavior.STATE_DRAGGING) {
					binding.destinationView.animate().scaleX(0F).scaleY(0F).setDuration(300).start()
				} else if (newState == GoogleMapsBottomSheetBehavior.STATE_COLLAPSED) {
					binding.destinationView.animate().scaleX(1F).scaleY(1F).setDuration(300).start()
				}
			}

			override fun onSlide(bottomSheet: View, slideOffset: Float) {}
		})
		binding.carBottomSheet.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
			override fun onGlobalLayout() {
				val layoutParam = CoordinatorLayout.LayoutParams(binding.carImageView.measuredWidth, bottomSheet.anchorOffset)
				binding.carImageView.layoutParams = layoutParam
				binding.carBottomSheet.viewTreeObserver.removeOnGlobalLayoutListener(this)
			}
		})

		bottomSheet.headerLayout?.let {
			it.findViewById<TextView>(R.id.carPlateNumberTextView).text = car.plateNumber
			it.findViewById<TextView>(R.id.provinceTextView).text = car.province
		}
		bottomSheet.contentLayout?.let {
			recyclerView = it.findViewById(R.id.statusRecyclerView)
			recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
			recyclerView.addItemDecoration(LayoutMarginDecoration(2, convertToPx(context!!, 2)))
			recyclerView.adapter = adapter
		}
	}

	override fun onMapReady(googleMap: GoogleMap?) {
		viewFlipperProgressBarOwn.hideProgressBar()
		googleMap?.let {
			mMap = it

			mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(13.7308051, 100.7806353)))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		mapView.onDestroy()
	}

	override fun onResume() {
		super.onResume()
		mapView.onResume()
	}

	override fun onPause() {
		super.onPause()
		mapView.onPause()
	}

	override fun onLowMemory() {
		super.onLowMemory()
		mapView.onLowMemory()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		mapView.onSaveInstanceState(outState)
	}
}