package com.example.storeapp.api
import com.example.storeapp.models.LoginRequest
import com.example.storeapp.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @POST("auth/login")
    fun login( @Body loginRequest: LoginRequest): Call<LoginResponse>
}
