package com.me.ascendexam.Database.TypeConverter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.me.ascendexam.Database.Model.ProductDetailModel
import java.lang.reflect.ParameterizedType

class ProductDetailConverter: BaseTypeConverter<ProductDetailModel>() {

    @TypeConverter
    override fun fromString(value: String): ArrayList<ProductDetailModel> {
        val typeToken = object : TypeToken<ArrayList<ProductDetailModel>>() {}
        val type = typeToken.type
        val list = GsonBuilder().create().fromJson<ArrayList<ProductDetailModel>>(value, type)
        return list ?: ArrayList()
    }
}