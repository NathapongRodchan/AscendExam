package com.me.ascendexam.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.me.ascendexam.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    companion object{
        var mainDisposable = CompositeDisposable()
    }

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainDisposable.clear()
    }
}