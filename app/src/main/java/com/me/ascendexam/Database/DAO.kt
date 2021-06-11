package com.me.ascendexam.Database

import androidx.room.*
import com.me.ascendexam.Database.Model.ProductDetailModel

@Dao
interface DAO {

    @Query("SELECT * FROM products")
    fun fetchProductListData(): MutableList<ProductDetailModel>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun fetchProductDetailData(productId: Int): ProductDetailModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProductData(data: ProductDetailModel)
}