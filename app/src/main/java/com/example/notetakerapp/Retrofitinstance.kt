package com.example.notetakerapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofitinstance {
    object RetrofitInstance {
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/posts")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(NoteApiService::class.java)
    }
}