package com.me.ascendexam.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.me.ascendexam.R
import com.me.ascendexam.Utils.GridSpacingItemDecoration
import com.me.ascendexam.Utils.LoadingState
import com.me.ascendexam.View.Adapter.ProductListAdapter
import com.me.ascendexam.ViewModel.ProductListViewModel
import com.me.ascendexam.databinding.FragmentProductListBinding
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment() : Fragment() {

    private var _viewBinding: FragmentProductListBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: ProductListViewModel by viewModel()
    private val onItemClick: (Int?) -> Unit = { productId -> openDetail(productId = productId) }
    private val productListAdapter by lazy { ProductListAdapter(onItemClick) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = FragmentProductListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        loadData()
        observeLoadingState(this.viewModel)
        observeData(this.viewModel)
    }

    private fun setView(){
        viewBinding.rvProductList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productListAdapter
            addItemDecoration(GridSpacingItemDecoration(requireContext(), 2, 16))
        }
    }

    private fun loadData(){
        viewModel.loadData()
    }

    private fun observeData(viewModel: ProductListViewModel) {
        viewModel.getLiveData().observe(viewLifecycleOwner, { data ->
            productListAdapter.updateData(data)
        })
    }

    private fun observeLoadingState(viewModel: ProductListViewModel){
        viewModel.getLoadingState().observe(viewLifecycleOwner, { state ->
            when(state){
                LoadingState.LOADING -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    viewBinding.tvState.visibility = View.GONE
                }
                LoadingState.SUCCESS -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.tvState.visibility = View.GONE
                }
                LoadingState.ERROR -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.tvStart.visibility = View.GONE
                    viewBinding.tvState.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.something_error)
                    }
                }
                LoadingState.NO_DATA -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.tvStart.visibility = View.GONE
                    viewBinding.tvState.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.cannot_connect_to_server)
                    }
                }
            }
        })
    }

    private fun openDetail(productId: Int?){
        productId?.let{id ->
            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productId = id)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}