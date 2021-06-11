package com.me.ascendexam

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Repo.LocalSource
import com.me.ascendexam.Repo.ProductDetailRepo
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Repo.RemoteSource
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductDetailRepoTest {

    @Mock
    lateinit var remoteSource: RemoteSource
    @Mock
    lateinit var localSource: LocalSource
    private lateinit var productDetailRepo: ProductDetailRepo

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        this.productDetailRepo = ProductDetailRepo(this.remoteSource, this.localSource)
    }

    @Test
    fun `when data source returns full data`(){
        // Setup
        val productBean = MockDataHelper.mockDataBean()
        val spyRepo = spy(this.productDetailRepo)
        `when`(this.remoteSource.fetchProductDetail(anyString())).thenAnswer {
            return@thenAnswer Observable.just(productBean)
        }
        `when`(this.localSource.fetchProductDetail(anyInt())).thenAnswer {
            return@thenAnswer Observable.just(productBean)
        }

        // Verify
        assertNotNull(spyRepo.fetchProductDetail(anyInt()).blockingLast())
        assertEquals(productBean, spyRepo.fetchProductDetail(anyInt()).blockingLast())
    }
}