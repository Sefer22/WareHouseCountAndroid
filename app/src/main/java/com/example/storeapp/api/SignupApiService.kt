package com.example.storeapp.api
import com.example.storeapp.models.SignupRequest
import com.example.storeapp.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApiService {

    @POST("auth/register")
    fun signup( @Body signupRequest: SignupRequest): Call<SignupResponse>
}
