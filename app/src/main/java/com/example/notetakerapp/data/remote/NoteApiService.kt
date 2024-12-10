package com.example.notetakerapp.data.remote
import com.example.notetakerapp.data.model.Note
import retrofit2.http.GET
interface NoteApiService {
    @GET("posts")
    fun fetchNotes():List<Note>
}