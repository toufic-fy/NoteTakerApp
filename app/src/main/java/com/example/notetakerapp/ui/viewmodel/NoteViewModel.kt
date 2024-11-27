package com.example.notetakerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notetakerapp.data.local.DatabaseProvider
import com.example.notetakerapp.data.model.Note
import com.example.notetakerapp.data.remote.RetrofitInstance
import com.example.notetakerapp.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val noteRepository = NoteRepository(DatabaseProvider.getDatabase(application).noteDao(), RetrofitInstance.RetrofitInstance.apiService)

    val allNotes: LiveData<List<Note>> = noteRepository.getAllNotes()

    fun refreshNotes(){
        viewModelScope.launch {
            noteRepository.refreshNotesIfNeeded()
        }
    }
}