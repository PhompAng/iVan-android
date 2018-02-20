package com.firebaseapp.ivan.ivan.ui.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Location
import com.firebaseapp.ivan.ivan.ui.map.MapActivity.Companion.EXTRA_LOCATION
import com.firebaseapp.ivan.util.inflate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * @author phompang on 19/2/2018 AD.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

	private lateinit var mapView: MapView
	private lateinit var mMap: GoogleMap
	private lateinit var location: Location

	companion object {
		val TAG: String = MapFragment::class.java.simpleName

		fun newInstance(location: Location) = MapFragment().apply {
			arguments = Bundle().apply {
				putParcelable(EXTRA_LOCATION, location)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.fragment_map)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		when (savedInstanceState) {
			null -> extractExtras(arguments)
			else -> extractExtras(savedInstanceState)
		}
		setUpMap(savedInstanceState)
	}

	private fun extractExtras(bundle: Bundle?) {
		bundle?.let {
			location = it.getParcelable(EXTRA_LOCATION)
		}
	}

	private fun setUpMap(savedInstanceState: Bundle?) {
		mapView = map
		mapView.onCreate(savedInstanceState)
		mapView.getMapAsync(this)
	}

	override fun onMapReady(googleMap: GoogleMap?) {
		googleMap?.let {
			mMap = it

			mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(13.7308051, 100.7806353)))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
			val latLng = LatLng(location.lat, location.lng)
			mMap.addMarker(MarkerOptions().position(latLng))
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
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
		outState.putParcelable(EXTRA_LOCATION, location)
	}
}