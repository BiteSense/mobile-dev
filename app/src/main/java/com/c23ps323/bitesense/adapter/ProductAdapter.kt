package com.c23ps323.bitesense.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.response.DataItem
import com.c23ps323.bitesense.databinding.ProductItemCardBinding
import com.c23ps323.bitesense.entities.Product

class ProductAdapter(private val listener: OnItemClickListener, private val listProduct: List<DataItem>) :
    ListAdapter<DataItem, ProductAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    class ViewHolder(private val binding: ProductItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindProduct(product: DataItem) {
            var isFavorite: Boolean = product.favorite == 1
            Glide.with(itemView)
                .load(product.fotoProduk)
                .into(binding.ivProduct)
            binding.apply {
                tvName.text = product.namaProduk
                tvCategory.text = product.tagProduk.toString()
                if (product.favorite == 1) ivFavorite.setImageResource(R.drawable.round_favorite_24) else ivFavorite.setImageResource(
                    R.drawable.round_favorite_border_24
                )
                ivFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        binding.ivFavorite.setImageResource(R.drawable.round_favorite_24)
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.round_favorite_border_24)
                    }
                }
                when(product.alert) {
                    0 -> binding.warningIndicator.apply {
                        setText(R.string.danger)
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.dangerColor)
                        isClickable = false
                    }
                    1 -> binding.warningIndicator.apply {
                        setText(R.string.warning)
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.warningColor)
                        isClickable = false
                    }
                    2 -> binding.warningIndicator.apply {
                        setText(R.string.safe)
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.safeColor)
                        isClickable = false
                    }
                    else -> binding.warningIndicator.apply {
                        setText(R.string.warning)
                        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.warningColor)
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

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(listProduct[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(listProduct[position].idProduk.toString())
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.idProduk == newItem.idProduk
            }
        }
    }
}