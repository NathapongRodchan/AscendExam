package com.me.ascendexam.Database.Model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class ProductDetailModel(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String?,
    @ColumnInfo(name = "isNewProduct")
    @SerializedName("isNewProduct")
    val isNewProduct: Boolean?,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: Double?,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?
)