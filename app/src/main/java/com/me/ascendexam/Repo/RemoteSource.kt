package com.me.ascendexam.Repo

import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Network.ApiClient
import io.reactivex.Observable

class RemoteSource(private val apiClient: ApiClient) {

    fun fetchProductList(): Observable<ArrayList<ProductDetailModel>> {
        return apiClient.getServices().getProductList()
    }

    fun fetchProductDetail(productId: String): Observable<ProductDetailModel> {
        return apiClient.getServices().getProductDetail(productId = productId)
    }
}