package com.me.ascendexam.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Utils.LoadingState
import com.me.ascendexam.View.MainActivity
import com.me.ascendexam.View.MainActivity.Companion.mainDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(private val productListRepo: ProductListRepo): ViewModel() {

    private val data = MutableLiveData<ArrayList<ProductDetailModel>>()
    private val loadingState = MutableLiveData(LoadingState.IDLE)

    fun loadData(){
        setLoadingState(LoadingState.LOADING)
        val disposable = productListRepo.fetchProductList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ArrayList<ProductDetailModel>>(){
                override fun onComplete() {
                    setLoadingState(LoadingState.SUCCESS)
                    if (data.value.isNullOrEmpty()) setLoadingState(LoadingState.NO_DATA)
                    Log.d("LOG_TAG", "List : completed")
                }
                override fun onNext(result: ArrayList<ProductDetailModel>) {
                    result?.let { productList ->
                        data.value = productList
                    }
                }
                override fun onError(e: Throwable) {
                    setLoadingState(LoadingState.ERROR)
                    Log.d("LOG_TAG", "List : error - ${e.message}")
                }
            })
        mainDisposable.add(disposable)
    }

    fun getLiveData(): MutableLiveData<ArrayList<ProductDetailModel>> = data

    fun getLoadingState(): MutableLiveData<Int> = loadingState

    private fun setLoadingState(state: Int){
        loadingState.value = state
    }
}