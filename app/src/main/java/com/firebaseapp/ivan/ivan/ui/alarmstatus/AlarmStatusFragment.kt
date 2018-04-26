package com.firebaseapp.ivan.ivan.ui.alarmstatus

import android.Manifest
import android.arch.lifecycle.ViewModelProvider
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.di.Injectable
import com.firebaseapp.ivan.ivan.model.AlarmStatus
import com.firebaseapp.ivan.ivan.model.Role
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.model.monad.fold
import com.firebaseapp.ivan.ivan.ui.alarmstatus.holder.AlarmStatusViewHolderFactory
import com.firebaseapp.ivan.ivan.ui.alarmstatus.holder.StaticMapViewHolderFactory
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.IVan
import com.firebaseapp.ivan.util.distanceBetween
import com.firebaseapp.ivan.util.inflate
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.view.LocationProgressDialog
import com.firebaseapp.ivan.util.view.ViewFlipperProgressBarOwn
import com.google.android.gms.location.*
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
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private lateinit var locationRequest: LocationRequest
	private lateinit var locationCallback: LocationCallback
	private var currentLocation: Location? = null
	private lateinit var locationProgressDialog: LocationProgressDialog

	private val user by lazy {
		IVan.getUser(context!!)
	}
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		locationProgressDialog = LocationProgressDialog(context!!)
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
		locationRequest = LocationRequest.create().apply {
			priority = LocationRequest.PRIORITY_HIGH_ACCURACY
			interval = 5000
		}

		locationCallback = object : LocationCallback() {
			override fun onLocationResult(result: LocationResult?) {
				result?.let {
					locationProgressDialog.dismiss()
					currentLocation = it.lastLocation
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		startLocationUpdates()
	}

	override fun onPause() {
		super.onPause()
		locationProgressDialog.dismiss()
		stopLocationUpdates()
	}

	private fun stopLocationUpdates() {
		fusedLocationClient.removeLocationUpdates(locationCallback)
	}

	private fun startLocationUpdates() {
		if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			activity?.finish()
		} else {
			fusedLocationClient.requestLocationUpdates(locationRequest,
					locationCallback,
					null /* Looper */)
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
		viewModel.getCallResult().observe(this) {
			it ?: return@observe
			if (it) {
				adapter.notifyDataSetChanged()
				val dialog = AlertDialog.Builder(context!!)
						.setTitle(R.string.complete)
						.setPositiveButton(R.string.action_close) { dialog, _ ->
							viewModel.setCallResult(false)
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
		adapter.registerViewHolderFactory(TYPE_1, AlarmStatusViewHolderFactory(ReportFalseAlarmListener(), ConfirmSecuredListener()))
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

	private inner class ConfirmSecuredListener : AlarmStatusViewHolderFactory.OnConfirmStudentSecuredListener {
		override fun onConfirm(data: AlarmStatus) {
			user.fold {
				onDriver {
					when {
						currentLocation == null -> getNoLocationDialog().show()
						!isInRange(data.location) -> getNotInRangeDialog().show()
						else -> viewModel.confirmSecured(data.uid, it.getKeyOrId(), it.fullName(), Role.DRIVER, getLocation(currentLocation!!.latitude, currentLocation!!.longitude))
					}
				}
				onTeacher {
					when {
						currentLocation == null -> getNoLocationDialog().show()
						!isInRange(data.location) -> getNotInRangeDialog().show()
						else -> viewModel.confirmSecured(data.uid, it.getKeyOrId(), it.fullName(), Role.TEACHER, getLocation(currentLocation!!.latitude, currentLocation!!.longitude))
					}
				}
			}
		}

		private fun isInRange(location: com.firebaseapp.ivan.ivan.model.Location): Boolean {
			val curLocation = com.firebaseapp.ivan.ivan.model.Location(currentLocation!!.latitude, currentLocation!!.longitude)
			val distance = distanceBetween(curLocation, location)
			Timber.d("$distance")
			return when (distance) {
				null -> false
				else -> distance <= 100
			}
		}


		private fun getNoLocationDialog(): AlertDialog.Builder {
			return AlertDialog.Builder(context!!)
					.setTitle(R.string.no_location)
					.setPositiveButton(R.string.action_close) { dialog, _ ->
						dialog.dismiss()
					}
		}

		private fun getNotInRangeDialog(): AlertDialog.Builder {
			return AlertDialog.Builder(context!!)
					.setTitle(R.string.not_in_range)
					.setPositiveButton(R.string.action_close) { dialog, _ ->
						dialog.dismiss()
					}
		}

		private fun getLocation(lat: Double, lng: Double): com.firebaseapp.ivan.ivan.model.Location {
			return com.firebaseapp.ivan.ivan.model.Location(lat, lng)
		}
	}
}