package com.firebaseapp.ivan.ivan.ui.carmap

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.util.DirectionConverter
import com.firebaseapp.ivan.ivan.BR
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.databinding.FragmentCarMapBinding
import com.firebaseapp.ivan.ivan.delegate.DelegateMobilityStatus
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.Route
import com.firebaseapp.ivan.ivan.model.Student
import com.firebaseapp.ivan.ivan.model.containNow
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.ui.carmap.viewholder.CarConditionViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.carmap.viewholder.CarStaffListViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.carmap.viewholder.MobilityStatusViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.GridItemDecoration
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.convertToPx
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.github.reline.GoogleMapsBottomSheetBehavior
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.wongnai.android.*
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
	private val adapter = MultipleViewAdapter(1)

	private val car by lazy {
		IVan.getCar(context!!)
	}
	private val user by lazy {
		IVan.getUser(context!!)
	}
	private val gson by lazy {
		Gson()
	}
	private val viewFlipperProgressBarOwn by lazy {
		ViewFlipperProgressBarOwn(binding.viewFlipper)
	}

	companion object {
		val TAG: String = CarMapFragment::class.java.simpleName

		fun newInstance(): CarMapFragment = CarMapFragment()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		retainInstance = true
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return DataBindingUtil.inflate<FragmentCarMapBinding>(inflater, R.layout.fragment_car_map, container, false).also {
			binding = it
		}.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewFlipperProgressBarOwn.showProgressBar()
		setUpMap(savedInstanceState)
		setUpBottomSheet()
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
		adapter.registerViewHolderFactory(TYPE_2, CarStaffListViewHolderFactory())
		adapter.registerViewHolderFactory(TYPE_3, CarConditionViewHolderFactory())
	}

	private fun setUpViewModel() {
		viewModel = activity!!.obtainViewModel(viewModelFactory, CarMapViewModel::class.java)
		viewModel.setCar(car)
		viewModel.getMobilityStatus().observe(this) {
			it ?: return@observe
			val latLng = LatLng(it.lat, it.lng)
			mMap.clear()
			mMap.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic36_van_top)))
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

			setUpRoute()
			adapter.clear()
			adapter.add(it, TYPE_3)
			adapter.add(DelegateMobilityStatus.getAvgSpeed(context!!, it), TYPE_0)
			adapter.add(DelegateMobilityStatus.getOilLevel(context!!, it), TYPE_1)
			adapter.add(DelegateMobilityStatus.getEngineOilMileage(context!!, it), TYPE_0)
			adapter.add(DelegateMobilityStatus.getBrakeOilMileage(context!!, it), TYPE_0)
			adapter.add(DelegateMobilityStatus.getSpeedExceed(context!!, it), TYPE_0)
			adapter.add(DelegateMobilityStatus.getMileage(context!!, it), TYPE_0)
			adapter.add(car, TYPE_2)
			adapter.notifyDataSetChanged()
		}
		viewModel.getSchool().observe(this) {
			it ?: return@observe
			binding.destinationTextView.text = it.name.th
			mMap.addMarker(MarkerOptions().position(LatLng(it.location.lat, it.location.lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination)))
		}
	}

	private fun setUpRoute() {
		if (car.time.morning.containNow()) {
			renderRoute(car.route.morning.waypoints, car.route.morning)
		} else if (car.time.evening.containNow()) {
			renderRoute(car.route.evening.waypoints, car.route.evening)
		}
	}

	private fun renderRoute(waypoints: List<Student>, route: Route) {
		user.fold {
			onDriver {
				for (i in waypoints) {
					mMap.addMarker(MarkerOptions().position(LatLng(i.location.lat, i.location.lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_waypoint)))
				}
			}
			onTeacher {
				for (i in waypoints) {
					mMap.addMarker(MarkerOptions().position(LatLng(i.location.lat, i.location.lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_waypoint)))
				}
			}
			onParent {
				for (i in waypoints) {
					if (it.location == i.location) {
						mMap.addMarker(MarkerOptions().position(LatLng(i.location.lat, i.location.lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_waypoint)))
					}
				}
			}
		}
		if (route.routes.isNotBlank()) {
			val direction = gson.fromJson(route.routes, Direction::class.java)
			direction?.let {
				val legList = it.routeList[0].legList
				for (leg in legList) {
					val polylineOptionList = DirectionConverter.createTransitPolyline(context, leg.stepList, 4, Color.DKGRAY, 5, Color.LTGRAY)
					for (polylineOption in polylineOptionList) {
						mMap.addPolyline(polylineOption)
					}
				}
			}
		}
	}

	private fun setUpBottomSheet() {
		bottomSheet = GoogleMapsBottomSheetBehavior.from(binding.carBottomSheet)
		bottomSheet.isHideable = false
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
				val layoutParam = CoordinatorLayout.LayoutParams(binding.carImageView.measuredWidth, binding.carImageView.measuredHeight)
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
			val layoutManager = GridLayoutManager(context, 2)
			layoutManager.spanSizeLookup = getSpanSizeLookUp()
			recyclerView.layoutManager = layoutManager
			recyclerView.isNestedScrollingEnabled = false
			val itemDecoration = GridItemDecoration(convertToPx(context!!, 2))
			itemDecoration.setSpaceTop(false)
			itemDecoration.setHasSpaceOnTheEdge(false)
			itemDecoration.setAdapter(adapter)
			itemDecoration.setSpanSizeLookup(getSpanSizeLookUp())
			recyclerView.addItemDecoration(itemDecoration)
			recyclerView.adapter = adapter
		}
	}

	private fun getSpanSizeLookUp() = object : GridLayoutManager.SpanSizeLookup() {
		override fun getSpanSize(position: Int): Int {
			return when (adapter.getItemViewType(position)) {
				TYPE_0, TYPE_1 -> 1
				TYPE_2, TYPE_3 -> 2
				else -> 1
			}
		}

	}

	override fun onMapReady(googleMap: GoogleMap?) {
		viewFlipperProgressBarOwn.hideProgressBar()
		googleMap?.let {
			mMap = it

			mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(13.7308051, 100.7806353)))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

			setUpViewModel()
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