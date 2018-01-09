package com.firebaseapp.ivan.ivan.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

/**
 * @author phompang on 9/1/2018 AD.
 */

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer<T> { t -> observer.invoke(t) })