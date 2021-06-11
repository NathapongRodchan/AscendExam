package com.me.ascendexam.Network

import com.me.ascendexam.Database.Model.ProductDetailModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Services {

    @GET("products")
    fun getProductList(): Observable<ArrayList<ProductDetailModel>>

    @GET("products/{productId}")
    fun getProductDetail(@Path("productId") productId: String): Observable<ProductDetailModel>
}