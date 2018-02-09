package com.firebaseapp.ivan.ivan.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.firebaseapp.ivan.ivan.di.ViewModelFactory

/**
 * @author phompang on 6/2/2018 AD.
 */

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
		ViewModelProviders.of(this, ViewModelFactory()).get(viewModelClass)