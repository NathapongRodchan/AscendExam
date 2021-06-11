package com.me.ascendexam

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.me.ascendexam.DI.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class BaseApp: Application() {

    companion object{ lateinit var BASE_CONTEXT : BaseApp }

    override fun onCreate() {
        super.onCreate()
        BASE_CONTEXT = this
        startKoin()
    }

    private fun startKoin(){
        startKoin {
            androidContext(BASE_CONTEXT)
            fragmentFactory()
            androidLogger()
            modules(KoinModule.getModule())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}