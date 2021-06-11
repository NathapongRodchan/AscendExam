package com.me.ascendexam.Repo

import android.util.Log
import com.me.ascendexam.Database.Model.ProductDetailModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductDetailRepo(private val remoteSource: RemoteSource, private val localSource: LocalSource) {

    fun fetchProductDetail(productId: Int): Observable<ProductDetailModel> {
        return Observable.concat(
            localSource.fetchProductDetail(productId),
            getRemoteData(productId.toString())
        )
            .subscribeOn(Schedulers.io())
            .doOnError { Log.d("LOG_TAG", "Detail : local source error - ${it.message}") }
            .onErrorResumeNext(getRemoteData(productId.toString()))
    }

    private fun getRemoteData(productId: String): Observable<ProductDetailModel> {
        return remoteSource
            .fetchProductDetail(productId)
            .doOnNext { saveData(it) }
            .doOnError { Log.d("LOG_TAG", "Detail : remote source error - ${it.message}") }
            .onErrorResumeNext(Observable.empty())
    }

    private fun saveData(data: ProductDetailModel) {
        localSource.saveProduct(data)
    }
}