package com.me.ascendexam.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Database.TypeConverter.ProductDetailConverter

@Database(entities = [ProductDetailModel::class], version = 1, exportSchema = false)
@TypeConverters(value = [ProductDetailConverter::class])
abstract class AppDatabase: RoomDatabase() {

    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            return INSTANCE?.let { it } ?: kotlin.run {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "Ascend.db").build()
                INSTANCE
            }
        }
    }

    abstract fun getDAO(): DAO
}