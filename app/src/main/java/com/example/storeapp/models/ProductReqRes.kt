package com.example.storeapp.models

data class ProductResponse(
    val message: String,
    val code: Int,
    val data: Array<Product>?
)