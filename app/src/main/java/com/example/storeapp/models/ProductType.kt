package com.example.storeapp.models

data class Product(
    val id: Int,
    val name: String,
    val checkCount: Int,
    val productCount: Int,
    val salesPrice: Double,
    val purchasePrice: Double,
    val qrCode: String,
    val categoryId: Int?
)
