package com.firebaseapp.ivan.ivan.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

/**
 * @author phompang on 6/2/2018 AD.
 */

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelFactory: ViewModelProvider.Factory, viewModelClass: Class<T>) =
		ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)