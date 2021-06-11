package com.me.ascendexam.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Repo.ProductDetailRepo
import com.me.ascendexam.Utils.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import com.me.ascendexam.View.MainActivity.Companion.mainDisposable

class ProductDetailViewModel(private val productDetailRepo: ProductDetailRepo): ViewModel()  {

    private val liveData = MutableLiveData<ProductDetailModel>()
    private val loadingState = MutableLiveData(LoadingState.IDLE)

    fun loadData(productId: Int){
        setLoadingState(LoadingState.LOADING)
        val disposable = productDetailRepo.fetchProductDetail(productId = productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ProductDetailModel>(){
                override fun onComplete() {
                    setLoadingState(LoadingState.SUCCESS)
                    if (liveData.value == null) setLoadingState(LoadingState.NO_DATA)
                    Log.d("LOG_TAG", "Detail : completed")
                }
                override fun onNext(result: ProductDetailModel) {
                    result?.let { product ->
                        liveData.value = product
                    }
                }
                override fun onError(e: Throwable) {
                    setLoadingState(LoadingState.ERROR)
                    Log.d("LOG_TAG", "Detail : error - ${e.message}")
                }
            })
        mainDisposable.add(disposable)
    }

    fun getLiveData(): MutableLiveData<ProductDetailModel> = liveData

    fun getLoadingState(): MutableLiveData<Int> = loadingState

    private fun setLoadingState(state: Int){
        loadingState.value = state
    }
}