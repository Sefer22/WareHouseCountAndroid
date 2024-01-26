package com.example.storeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeapp.api.SignupApiService
import com.example.storeapp.models.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.storeapp.models.SignupRequest

class FragmentSignup : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        val button = view.findViewById<Button>(R.id.signupsignupButton)

        val usernameEditText = view.findViewById<EditText>(R.id.signupTextUsername)
        val passwordEditText = view.findViewById<EditText>(R.id.signupTextPassword)
        val fullnameEditText = view.findViewById<EditText>(R.id.signupTextFullname)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.117:8080/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(SignupApiService::class.java)

        button.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullname = fullnameEditText.text.toString()

            val signupRequest =SignupRequest(email, password, fullname)
            val call = apiService.signup(signupRequest)
            call.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if (response.isSuccessful) {
//                        val token = response.body()?.token

//                        if (token != null) {
//                            Log.d("Token", "Alınan token: $token")
                            findNavController().navigate(R.id.action_fragmentSignup_to_fragmentLogin)

//                        } else {
//                            Toast.makeText(activity, "Token null", Toast.LENGTH_SHORT).show()
//                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Geçersiz kullanıcı adı veya şifre",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("ApiCall", "Retrofit onFailure: ${t.message}")
                    Toast.makeText(activity, "Ağ hatası", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return view
    }
}
