package com.example.notetakerapp

import androidx.room.Query

interface NoteDao {
    @Query("Select * FROM Note")
    suspend fun getNotes() : List<Note>
}