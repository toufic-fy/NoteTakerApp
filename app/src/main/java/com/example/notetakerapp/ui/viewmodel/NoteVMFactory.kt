package com.example.notetakerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notetakerapp.data.repository.NoteRepository

class NoteVMFactory(
    private val application: Application,
    private val noteRepository: NoteRepository?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(application, noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
