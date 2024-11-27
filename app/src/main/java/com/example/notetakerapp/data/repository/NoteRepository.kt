package com.example.notetakerapp.data.repository

import androidx.lifecycle.LiveData
import com.example.notetakerapp.data.remote.NoteApiService
import com.example.notetakerapp.data.local.NoteDao
import com.example.notetakerapp.data.model.Note

class NoteRepository(private val noteDao: NoteDao, private val noteApiService: NoteApiService) {
    fun getAllNotes(): LiveData<List<Note>> = noteDao.getNotes()

    suspend fun fetchNotesFromApi(): List<Note> = noteApiService.fetchNotes()

    suspend fun refreshNotesIfNeeded(){
        //implement logic to retrieve notes after the last insert date
    }
}