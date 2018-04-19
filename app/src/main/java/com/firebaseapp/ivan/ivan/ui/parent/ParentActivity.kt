package com.firebaseapp.ivan.ivan.ui.parent

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.firebaseapp.ivan.ivan.R
import com.firebaseapp.ivan.ivan.model.Parent
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
 * @author phompang on 20/2/2018 AD.
 */
class ParentActivity : AppCompatActivity(), HasSupportFragmentInjector {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Fragment>
	private lateinit var viewModel: ParentViewModel
	private var parentUid = ""

	companion object {
		const val EXTRA_PARENT_ID = "extra-parent-id"
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
						ParentFragment(),
						ParentFragment.TAG,
						true,
						R.id.flContent
				)
			}
			else -> extractExtras(savedInstanceState)
		}

		setUpViewModel()
	}

	private fun extractExtras(bundle: Bundle) {
		parentUid = bundle.getString(EXTRA_PARENT_ID)
	}

	private fun setUpViewModel() {
		viewModel = obtainViewModel(viewModelFactory, ParentViewModel::class.java)
		viewModel.setParentUid(parentUid)
		viewModel.getParent().observe(this) {
			it ?: return@observe
			collapsingToolbarLayout.title = it.fullName()
			DataBindingUtils.loadFromFirebaseStorage(headerImageView, it, getDrawable(R.color.colorPrimary), NONE)

			setData(it)
		}
	}

	private fun setData(parent: Parent) {
		val fragment = supportFragmentManager.findFragmentByTag(ParentFragment.TAG)
		if (fragment is ParentFragment) {
			fragment.setParent(parent)
			fragment.notifyDataSetChanged()
		}
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