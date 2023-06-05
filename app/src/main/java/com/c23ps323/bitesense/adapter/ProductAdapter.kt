package com.c23ps323.bitesense.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.ProductItemCardBinding
import com.c23ps323.bitesense.entities.Product

class ProductAdapter(private val listProduct: List<Product>) :
    ListAdapter<Product, ProductAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    class ViewHolder(private val binding: ProductItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindProduct(product: Product) {
            Glide.with(itemView)
                .load(product.photoUrl)
                .into(binding.ivProduct)
            binding.apply {
                tvName.text = product.name
                tvCategory.text = product.category
                if (product.isFavorite) ivFavorite.setImageResource(R.drawable.round_favorite_24) else ivFavorite.setImageResource(
                    R.drawable.round_favorite_border_24
                )
                ivFavorite.setOnClickListener {
                    product.isFavorite = !product.isFavorite
                    if (product.isFavorite) {
                        binding.ivFavorite.setImageResource(R.drawable.round_favorite_24)
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.round_favorite_border_24)
                    }
                }
                when(product.warningIndicator) {
                    0 -> binding.ivIndicator.setImageResource(R.drawable.danger_indicator)
                    1 -> binding.ivIndicator.setImageResource(R.drawable.warning_indicator)
                    2 -> binding.ivIndicator.setImageResource(R.drawable.safe_indicator)
                    else -> binding.ivIndicator.setImageResource(R.drawable.warning_indicator)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(listProduct[position])
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}