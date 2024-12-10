package com.example.notetakerapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.notetakerapp.data.model.Note

interface NoteDao {
    @Query("Select * FROM Note")
    fun getNotes() : LiveData<List<Note>>
}