package com.firebaseapp.ivan.ivan.map

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.api.model.RestApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : FragmentActivity(), OnMapReadyCallback {

	private lateinit var mMap: GoogleMap
	private lateinit var mDatabaseRef: DatabaseReference
	private lateinit var adapter: ArrayAdapter<String>
	private val restApi by lazy {
		RestApi()
	}
	private val cars = mutableListOf<String>()
	private var current = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_map)

		mDatabaseRef = FirebaseDatabase.getInstance().reference
		adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cars)
		spinner.adapter = adapter
		spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(p0: AdapterView<*>?) {

			}

			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				current = cars[position]
				Log.d("map", current)
			}
		}

		val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)

		loadCars()
	}

	override fun onMapReady(googleMap: GoogleMap?) {
		googleMap?.let {
			mMap = googleMap

			mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(13.7308051, 100.7806353)))
			mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
			mMap.setOnMapClickListener { latLng ->
				mMap.clear()
				mMap.addMarker(MarkerOptions().position(latLng))
				restApi.postMobility(current, latLng)
			}
		}
	}


	private fun loadCars() {
		mDatabaseRef.child("cars").addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onCancelled(p0: DatabaseError?) {

			}

			override fun onDataChange(dataSnapshot: DataSnapshot?) {
				dataSnapshot?.let {
					cars.clear()
					dataSnapshot.children.mapNotNullTo(cars) {
						it.key
					}
					current = cars.first()
					adapter.notifyDataSetChanged()
				}
			}
		})
	}
}
