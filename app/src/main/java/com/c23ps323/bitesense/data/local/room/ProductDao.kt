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
    @Query("SELECT * FROM products ORDER BY name DESC")
    fun getProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products where isFavorite = 1")
    fun getFavoriteProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProducts(news: List<ProductEntity>)

    @Update
    suspend fun updateProduct(news: ProductEntity)

    @Query("DELETE FROM products WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM products WHERE name = :name AND isFavorite = 1)")
    suspend fun isFavoriteProduct(name: String): Boolean
}