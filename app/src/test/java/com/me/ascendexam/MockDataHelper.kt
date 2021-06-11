package com.me.ascendexam

import com.me.ascendexam.Database.Model.ProductDetailModel

object MockDataHelper {

    fun mockFullListData(size: Int): ArrayList<ProductDetailModel> {
        val list = ArrayList<ProductDetailModel>()
        for (index in 1..size) {
            list.add(
                ProductDetailModel(
                index,
                "mock content",
                "mock image",
                true,
                99.99,
                "mock title"
                )
            )
        }
        return list
    }

    fun mockEmptyListData(): ArrayList<ProductDetailModel> {
        return ArrayList()
    }

    fun mockDataBean(): ProductDetailModel {
        return ProductDetailModel(
            1,
            "mock content",
            "mock image",
            true,
            99.99,
            "mock title"
        )
    }
}