package com.me.ascendexam

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Repo.ProductDetailRepo
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Utils.LoadingState
import com.me.ascendexam.ViewModel.ProductDetailViewModel
import com.me.ascendexam.ViewModel.ProductListViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.*

import org.mockito.Mockito.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ProductDetailViewModelTest {

    @Mock
    lateinit var repository : ProductDetailRepo
    private lateinit var viewModel : ProductDetailViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        this.viewModel = ProductDetailViewModel(this.repository)
    }

    @Test
    fun `when repository returns full data`(){
        // Setup
        val productBean = MockDataHelper.mockDataBean()
        val spyViewModel = spy(this.viewModel)
        val observer = mock(Observer::class.java) as Observer<ProductDetailModel>
        `when`(this.repository.fetchProductDetail(anyInt())).thenAnswer {
            return@thenAnswer Observable.just(productBean)
        }

        // Given
        spyViewModel.getLiveData().observeForever(observer)

        // Invoke
        spyViewModel.loadData(anyInt())

        // Verify
        assertNotNull(spyViewModel.getLiveData().value)
        assertEquals(productBean, spyViewModel.getLiveData().value)
        assertEquals(LoadingState.SUCCESS, spyViewModel.getLoadingState().value)
    }
}