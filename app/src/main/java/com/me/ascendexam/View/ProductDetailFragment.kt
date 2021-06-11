package com.me.ascendexam.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.me.ascendexam.R
import com.me.ascendexam.Utils.LoadingState
import com.me.ascendexam.ViewModel.ProductDetailViewModel
import com.me.ascendexam.databinding.FragmentProductDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private var _viewBinding: FragmentProductDetailBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val args: ProductDetailFragmentArgs by navArgs()
    private val viewModel: ProductDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        getData()
        observeLoadingState(this.viewModel)
        observeData(this.viewModel)
    }

    private fun setView(){
        viewBinding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun getData(){
        viewModel.loadData(args.productId)
    }

    private fun observeData(viewModel: ProductDetailViewModel) {
        viewModel.getLiveData().observe(viewLifecycleOwner, { product ->
            viewBinding.apply {
                Glide.with(requireContext()).load(product.image).centerCrop().placeholder(R.drawable.place_holder).into(ivProduct)
                tvName.text = product.title
                tvPrice.text = product.price.toString()
                tvContent.text = product.content
                tvNew.visibility = if (product.isNewProduct == true) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    private fun observeLoadingState(viewModel: ProductDetailViewModel){
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
                    viewBinding.scrollView.visibility = View.GONE
                    viewBinding.tvState.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.something_error)
                    }
                }
                LoadingState.NO_DATA -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.tvStart.visibility = View.GONE
                    viewBinding.scrollView.visibility = View.GONE
                    viewBinding.tvState.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.cannot_connect_to_server)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}