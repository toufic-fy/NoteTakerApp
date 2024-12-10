package com.example.notetakerapp.data.remote

import com.example.notetakerapp.data.adapters.DateTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

object RetrofitInstance {
    private const val BASE_URL = "https://api.mockapi.com"
    private const val API_KEY = "1de4ca4a017345279a2279d65868196a"
    private const val API_KEY_HEADER = "x-api-key"
    object RetrofitInstance {
        private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .create()

        private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient())
            .build()

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val requestWithApiKey = originalRequest.newBuilder()
                        .header(API_KEY_HEADER, API_KEY)
                        .build()
                    chain.proceed(requestWithApiKey)
                }
                .build()
        }

        val noteApiService: NoteApiService = retrofit.create(NoteApiService::class.java)
    }
}