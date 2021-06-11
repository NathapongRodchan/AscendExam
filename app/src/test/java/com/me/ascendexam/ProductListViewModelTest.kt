package com.me.ascendexam

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.Repo.ProductListRepo
import com.me.ascendexam.Utils.LoadingState
import com.me.ascendexam.ViewModel.ProductListViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductListViewModelTest {

    @Mock lateinit var repository : ProductListRepo
    private lateinit var viewModel : ProductListViewModel
    private val size = 5

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        this.viewModel = ProductListViewModel(this.repository)
    }

    @Test
    fun `when repository returns full data`(){
        // Setup
        val dataList = MockDataHelper.mockFullListData(size)
        val spyViewModel = spy(this.viewModel)
        val observer = mock(Observer::class.java) as Observer<ArrayList<ProductDetailModel>>
        `when`(this.repository.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(dataList)
        }

        // Given
        spyViewModel.getLiveData().observeForever(observer)

        // Invoke
        spyViewModel.loadData()

        // Verify
        assertNotNull(spyViewModel.getLiveData().value)
        assertEquals(size, spyViewModel.getLiveData().value?.size)
        assertEquals(dataList, spyViewModel.getLiveData().value)
        assertEquals(LoadingState.SUCCESS, spyViewModel.getLoadingState().value)
    }

    @Test
    fun `when repository returns empty data`(){
        // setup
        val emptyList = MockDataHelper.mockEmptyListData()
        val spyViewModel = spy(this.viewModel)
        val observer = mock(Observer::class.java) as Observer<ArrayList<ProductDetailModel>>
        `when`(this.repository.fetchProductList()).thenAnswer {
            return@thenAnswer Observable.just(emptyList)
        }

        // given
        spyViewModel.getLiveData().observeForever(observer)

        // invoke
        spyViewModel.loadData()

        // Verify
        assertNotNull(spyViewModel.getLiveData().value)
        assertEquals(0, spyViewModel.getLiveData().value?.size)
        assertEquals(emptyList, spyViewModel.getLiveData().value)
        assertEquals(LoadingState.NO_DATA, spyViewModel.getLoadingState().value)
    }

    @After
    fun after(){

    }
}