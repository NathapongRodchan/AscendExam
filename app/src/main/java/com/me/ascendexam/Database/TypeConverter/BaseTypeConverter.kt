package com.me.ascendexam.Database.TypeConverter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder

abstract class BaseTypeConverter<T> {

    @TypeConverter
    abstract fun fromString(value: String): ArrayList<T>

    @TypeConverter
    fun fromArrayList(value: ArrayList<T>): String {
        val json = GsonBuilder().create().toJson(value)
        return json ?: ""
    }
}