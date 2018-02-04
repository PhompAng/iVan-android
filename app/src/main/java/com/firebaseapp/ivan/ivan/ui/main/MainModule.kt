package com.firebaseapp.ivan.ivan.ui.main

import android.arch.lifecycle.ViewModel
import com.firebaseapp.ivan.ivan.di.ViewModelKey
import com.firebaseapp.ivan.ivan.ui.map.CarMapFragment
import com.firebaseapp.ivan.ivan.ui.map.CarMapViewModel
import com.firebaseapp.ivan.ivan.ui.select.SelectCarViewModel
import com.firebaseapp.ivan.ivan.ui.select.SelectCarFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * @author phompang on 16/1/2018 AD.
 */
/**
 * @author phompang on 16/1/2018 AD.
 */
@Module
internal abstract class MainModule {

	@Binds
	@IntoMap
	@ViewModelKey(MainViewModel::class)
	abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(SelectCarViewModel::class)
	abstract fun bindSelectCarViewModel(viewModel: SelectCarViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeSelectCarFragment(): SelectCarFragment

	@Binds
	@IntoMap
	@ViewModelKey(CarMapViewModel::class)
	abstract fun bindCarMapViewModel(viewModel: CarMapViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeCarMapFragment(): CarMapFragment
}