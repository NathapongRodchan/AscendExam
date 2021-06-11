package com.me.ascendexam.DI

import com.me.ascendexam.Network.ApiClient
import com.me.ascendexam.Repo.LocalSource
import com.me.ascendexam.Repo.ProductDetailRepo
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Repo.RemoteSource
import com.me.ascendexam.ViewModel.ProductDetailViewModel
import com.me.ascendexam.ViewModel.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule {

    fun getModule(): Module {
        return module {
            single { ApiClient }
            single { RemoteSource(get()) }
            single { LocalSource() }
            single { ProductListRepo(get(), get()) }
            viewModel { ProductListViewModel(get()) }
            single { ProductDetailRepo(get(), get()) }
            viewModel { ProductDetailViewModel(get()) }
        }
    }
}


