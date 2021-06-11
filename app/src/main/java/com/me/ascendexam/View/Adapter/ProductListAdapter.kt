package com.me.ascendexam.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.me.ascendexam.Database.Model.ProductDetailModel
import com.me.ascendexam.R
import com.me.ascendexam.databinding.ProductListAdapterLayoutBinding
import kotlin.collections.ArrayList

class ProductListAdapter(private val onItemClick:(Int?) -> Unit): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val itemList by lazy { ArrayList<ProductDetailModel>() }

    fun updateData(data: ArrayList<ProductDetailModel>){
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ProductListAdapterLayoutBinding .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    inner class ViewHolder(private val viewBinding: ProductListAdapterLayoutBinding): RecyclerView.ViewHolder(viewBinding.root){

        fun bind(product: ProductDetailModel){
            viewBinding.apply {
                viewBinding.root.context?.let { context ->
                    Glide.with(context).load(product.image).centerCrop().placeholder(R.drawable.place_holder).into(ivProduct)
                    tvName.text = product.title
                    tvPrice.text = product.price.toString()
                    tvNew.visibility = if (product.isNewProduct == true) View.VISIBLE else View.INVISIBLE
                    root.setOnClickListener{ onItemClick(product.id) }
                }
            }
        }
    }
}