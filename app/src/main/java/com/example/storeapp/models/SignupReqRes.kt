package com.example.storeapp.models

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("successfullly registered heh:)")
    val token: String
)
data class SignupRequest(val email: String, val password: String, val fullName: String)
