package com.c23ps323.bitesense.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class ProductEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("photoUrl")
    val photoUrl: String,

    @ColumnInfo("category")
    val category: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("composition")
    val composition: String,

    @ColumnInfo("warningIndicator")
    val warningIndicator: Int,

    @ColumnInfo("isFavorite")
    var isFavorite: Boolean
)