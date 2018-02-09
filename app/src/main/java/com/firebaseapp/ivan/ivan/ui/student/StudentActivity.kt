package com.firebaseapp.ivan.ivan.ui.student

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.util.replaceFragmentSafely
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

/**
 * @author phompang on 4/2/2018 AD.
 */
class StudentActivity : AppCompatActivity(), HasSupportFragmentInjector {
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>
	private var studentUid = ""

	companion object {
		const val EXTRA_STUDENT_UID = "extra-student-uid"
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.app_bar_main)
		setSupportActionBar(toolbar)

		when (savedInstanceState) {
			null -> {
				extractExtras(intent.extras)
				replaceFragmentSafely(
						StudentFragment.newInstance(studentUid),
						StudentFragment.TAG,
						true,
						R.id.flContent
				)
			}
			else -> extractExtras(savedInstanceState)
		}
	}

	private fun extractExtras(bundle: Bundle) {
		studentUid = bundle.getString(EXTRA_STUDENT_UID, "")
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putString(EXTRA_STUDENT_UID, studentUid)
	}
}