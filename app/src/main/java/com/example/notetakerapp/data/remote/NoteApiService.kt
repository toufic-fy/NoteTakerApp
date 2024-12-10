package com.example.notetakerapp.data.remote
import com.example.notetakerapp.data.model.Note
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApiService {
    @GET("/api/v1/notes/list")
    suspend fun getNotes():List<Note>

    @POST("/api/v1/notes/create")
    suspend fun createNote(@Body note: Note): Note

    @PUT("/api/v1/notes/{id}")
    suspend fun updateNote(@Path("id") id: Long, @Body note: Note): Note

    @DELETE("/api/v1/notes/{id}")
    suspend fun deleteNote(@Path("id") id: Long)
}