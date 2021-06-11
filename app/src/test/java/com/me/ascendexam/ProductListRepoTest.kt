package com.me.ascendexam

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.ascendexam.Repo.LocalSource
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Repo.RemoteSource
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class ProductListRepoTest {

    @Mock lateinit var remoteSource: RemoteSource
    @Mock lateinit var localSource: LocalSource
    private lateinit var productListRepo : ProductListRepo
    private val size = 5

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        this.productListRepo = ProductListRepo(this.remoteSource, this.localSource)
    }

    @Test
    fun `when data source returns full data`(){
        // Setup
        val dataList = MockDataHelper.mockFullListData(size)
        val spyRepo = spy(this.productListRepo)
        `when`(this.remoteSource.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(dataList)
        }
        `when`(this.localSource.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(dataList)
        }

        // Verify
        assertNotNull(spyRepo.fetchProductList().blockingLast())
        assertFalse(spyRepo.fetchProductList().blockingLast().isEmpty())
        assertEquals(size, spyRepo.fetchProductList().blockingLast().size)
        assertEquals(dataList, spyRepo.fetchProductList().blockingLast())
    }

    @Test
    fun `when data source returns empty data`(){
        // Setup
        val emptyList = MockDataHelper.mockEmptyListData()
        val spyRepo = spy(this.productListRepo)
        `when`(this.remoteSource.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(emptyList)
        }
        `when`(this.localSource.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(emptyList)
        }

        // Verify
        assertNotNull(spyRepo.fetchProductList().blockingLast())
        assertTrue(spyRepo.fetchProductList().blockingLast().isEmpty())
        assertEquals(0, spyRepo.fetchProductList().blockingLast().size)
        assertEquals(emptyList, spyRepo.fetchProductList().blockingLast())
    }
}