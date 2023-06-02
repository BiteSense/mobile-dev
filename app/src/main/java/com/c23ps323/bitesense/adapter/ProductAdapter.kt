package com.c23ps323.bitesense.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.local.entity.ProductEntity
import com.c23ps323.bitesense.databinding.ProductItemCardBinding

class ProductAdapter(
    private val listener: OnItemClickListener,
    private val onFavoriteClick: (ProductEntity) -> Unit
) :
    ListAdapter<ProductEntity, ProductAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    class ViewHolder(val binding: ProductItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindProduct(product: ProductEntity) {
            Glide.with(itemView)
                .load(product.photoUrl)
                .into(binding.ivProduct)
            binding.apply {
                tvName.text = product.name
                tvCategory.text = product.category
                if (product.isFavorite) ivFavorite.setImageResource(R.drawable.round_favorite_24) else ivFavorite.setImageResource(
                    R.drawable.round_favorite_border_24
                )
                when (product.warningIndicator) {
                    0 -> binding.warningIndicator.apply {
                        setText(R.string.danger)
                        chipBackgroundColor =
                            ContextCompat.getColorStateList(context, R.color.dangerColor)
                        isClickable = false
                    }

                    1 -> binding.warningIndicator.apply {
                        setText(R.string.warning)
                        chipBackgroundColor =
                            ContextCompat.getColorStateList(context, R.color.warningColor)
                        isClickable = false
                    }

                    2 -> binding.warningIndicator.apply {
                        setText(R.string.safe)
                        chipBackgroundColor =
                            ContextCompat.getColorStateList(context, R.color.safeColor)
                        isClickable = false
                    }

                    else -> binding.warningIndicator.apply {
                        setText(R.string.warning)
                        chipBackgroundColor =
                            ContextCompat.getColorStateList(context, R.color.warningColor)
                        isClickable = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = getItem(position)
        holder.bindProduct(products)

        holder.itemView.setOnClickListener {
            listener.onItemClick(products.id)
        }
        val ivFavorite = holder.binding.ivFavorite
        if (products.isFavorite) {
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.round_favorite_24))
        } else {
            ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.round_favorite_border_24))
        }
        ivFavorite.setOnClickListener {
            products.isFavorite = !products.isFavorite
            onFavoriteClick(products)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProductEntity,
                newItem: ProductEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}