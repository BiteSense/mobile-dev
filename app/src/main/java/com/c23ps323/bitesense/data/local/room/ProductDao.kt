package com.c23ps323.bitesense.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.c23ps323.bitesense.data.local.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY product_id DESC")
    fun getProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products ORDER BY product_id DESC LIMIT 5")
    fun getLastProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products ORDER BY product_id DESC LIMIT 1")
    fun getScannedProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products where isFavorite = 1 GROUP BY name")
    fun getFavoriteProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(product: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Update
    suspend fun updateProductHistory(product: List<ProductEntity>)

    @Query("DELETE FROM products WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("DELETE FROM products")
    suspend fun deleteTable()

    @Query("SELECT EXISTS(SELECT * FROM products WHERE product_id = :id AND isFavorite = 1)")
    suspend fun isFavoriteProduct(id: String): Boolean
}