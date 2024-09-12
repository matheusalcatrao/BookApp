package com.example.bookapp.service

import com.example.bookapp.model.Book
import com.example.bookapp.model.LoginRequest
import com.example.bookapp.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")  // Replace with your actual login endpoint
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("books")
    fun getAllBooks(@Header("Authorization") token: String): Call<List<Book>>
}
