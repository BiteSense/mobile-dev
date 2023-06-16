package com.c23ps323.bitesense.entities

data class HealthCondition(
    val penyakit: List<PenyakitItem>,
    val kondisi: List<ConditionItem>,
    val makanan: List<FoodItem>,
)
