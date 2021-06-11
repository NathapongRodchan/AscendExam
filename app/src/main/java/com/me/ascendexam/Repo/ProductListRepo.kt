package com.me.ascendexam.Repo

import android.util.Log
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.View.MainActivity
import com.me.ascendexam.View.MainActivity.Companion.mainDisposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductListRepo (private val remoteSource: RemoteSource, private val localSource: LocalSource) {

    fun fetchProductList(): Observable<ArrayList<ProductDetailModel>> {
        return Observable.concat(
            localSource.fetchProductList(),
            getRemoteData()
        )
            .subscribeOn(Schedulers.io())
            .doOnError { Log.d("LOG_TAG", "List : local source error - ${it.message}") }
            .onErrorResumeNext(getRemoteData())
    }

    private fun getRemoteData(): Observable<ArrayList<ProductDetailModel>> {
        return remoteSource
            .fetchProductList()
            .doOnNext { saveData(it) }
            .doOnError { Log.d("LOG_TAG", "List : remote source error - ${it.message}") }
            .onErrorResumeNext(Observable.empty())
    }

    private fun saveData(productList: ArrayList<ProductDetailModel>) {
        mainDisposable.add(
            Observable.fromIterable(productList)
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    localSource.saveProduct(data)
                }
        )
    }
}