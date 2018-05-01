package com.firebaseapp.ivan.ivan.ui.teacher

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Teacher
import com.firebaseapp.ivan.ivan.model.fullName
import com.firebaseapp.ivan.ivan.utils.obtainViewModel
import com.firebaseapp.ivan.util.DataBindingUtils
import com.firebaseapp.ivan.util.glide.GlideTransformClass.Companion.NONE
import com.firebaseapp.ivan.util.observe
import com.firebaseapp.ivan.util.replaceFragmentSafely
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.collapsing_toolbar_main.*
import javax.inject.Inject

/**
 * @author phompang on 6/4/2018 AD.
 */
class TeacherActivity : LocalizationActivity(), HasSupportFragmentInjector {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

	private lateinit var viewModel: TeacherViewModel
	private var teacherUid = ""

	companion object {
		const val EXTRA_TEACHER_ID = "extra-teacher-id"
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = androidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.collapsing_toolbar_main)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		when (savedInstanceState) {
			null -> {
				extractExtras(intent.extras)
				replaceFragmentSafely(
						TeacherFragment(),
						TeacherFragment.TAG,
						true,
						R.id.flContent
				)
			}
			else -> extractExtras(savedInstanceState)
		}

		setUpViewModel()
	}

	private fun extractExtras(bundle: Bundle) {
		teacherUid = bundle.getString(EXTRA_TEACHER_ID)
	}

	private fun setUpViewModel() {
		viewModel = obtainViewModel(viewModelFactory, TeacherViewModel::class.java)
		viewModel.setTeacherUid(teacherUid)
		viewModel.getTeacher().observe(this) {
			it ?: return@observe
			collapsingToolbarLayout.title = it.fullName()
			DataBindingUtils.loadFromFirebaseStorage(headerImageView, it, getDrawable(R.color.colorPrimary), NONE)

			setData(it)
		}
	}

	private fun setData(teacher: Teacher) {
		val fragment = supportFragmentManager.findFragmentByTag(TeacherFragment.TAG)
		if (fragment is TeacherFragment) {
			fragment.setTeacher(teacher)
			fragment.notifyDataSetChanged()
		}
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		outState?.putString(EXTRA_TEACHER_ID, teacherUid)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item?.itemId) {
			android.R.id.home -> {
				finish()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}
}