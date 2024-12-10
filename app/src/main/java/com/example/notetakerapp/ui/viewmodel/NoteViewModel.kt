package com.example.notetakerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notetakerapp.data.local.DatabaseProvider
import com.example.notetakerapp.data.model.Note
import com.example.notetakerapp.data.remote.Resource
import com.example.notetakerapp.data.remote.RetrofitInstance
import com.example.notetakerapp.data.repository.NoteRepository
import kotlinx.coroutines.launch
import java.util.Date

class NoteViewModel(application: Application, noteRepository: NoteRepository? = null): AndroidViewModel(application) {
    constructor(application: Application): this(application, null)

    private val _noteRepository: NoteRepository = noteRepository ?: NoteRepository(DatabaseProvider.getDatabase(application).noteDao(),
        RetrofitInstance.RetrofitInstance.noteApiService,
        application.applicationContext)

    // Local database notes
    val localNotes: LiveData<List<Note>> = _noteRepository.allNotes.asLiveData()

    // Network notes with loading and error states
    private val _networkNotes = MutableLiveData<Resource<List<Note>>>()
    val networkNotes: LiveData<Resource<List<Note>>> = _networkNotes

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            _noteRepository.getNotes().collect { resource ->
                _networkNotes.value = resource
            }
        }
    }

    fun createNote(title: String, content: String, createdAt: Date){
        viewModelScope.launch {
            try{
                _noteRepository.createNote(title, content, createdAt)
            }catch (e: Exception){
                _networkNotes.value = Resource.Error("Failed to create note")
            }
        }
    }
}