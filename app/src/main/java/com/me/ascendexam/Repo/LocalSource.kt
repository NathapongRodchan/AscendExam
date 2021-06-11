package com.me.ascendexam.Repo

import com.me.ascendexam.BaseApp
import com.me.ascendexam.Database.AppDatabase
import com.me.ascendexam.Database.Model.ProductDetailModel
import io.reactivex.Observable

class LocalSource {

    fun fetchProductList(): Observable<ArrayList<ProductDetailModel>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(BaseApp.BASE_CONTEXT)?.getDAO()?.fetchProductListData() as ArrayList
        }
    }

    fun fetchProductDetail(productId: Int): Observable<ProductDetailModel> {
        return Observable.fromCallable {
            AppDatabase.getInstance(BaseApp.BASE_CONTEXT)?.getDAO()?.fetchProductDetailData(productId)
        }
    }

    fun saveProduct(data: ProductDetailModel) {
        AppDatabase.getInstance(BaseApp.BASE_CONTEXT)?.getDAO()?.saveProductData(data = data)
    }
}