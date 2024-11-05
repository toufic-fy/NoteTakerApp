package com.example.notetakerapp
import retrofit2.Call
import retrofit2.http.GET
interface NoteApiService {
    @GET("posts")
    fun fetchNotes():Call<List<Note>>
}