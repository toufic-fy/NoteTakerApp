package com.example.notetakerapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    object RetrofitInstance {
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/posts")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(NoteApiService::class.java)
    }
}