package com.example.notetakerapp.data.repository

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notetakerapp.data.remote.NoteApiService
import com.example.notetakerapp.data.local.NoteDao
import com.example.notetakerapp.data.model.Note
import com.example.notetakerapp.data.remote.NoteSyncWorker
import com.example.notetakerapp.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

// NoteRepository.kt
class NoteRepository(
    private val noteDao: NoteDao,
    private val apiService: NoteApiService,
    private val context: Context
) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    fun getNotes(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading())
        try{
            val networkNotes = apiService.getNotes()

            networkNotes.forEach {note: Note ->
                noteDao.insertNote(note)
            }

            emit(Resource.Success(networkNotes))
        }catch (e: Exception){
            emit(Resource.Error("Failed to fetch notes ${e.message}"))
        }
    }.flowOn(Dispatchers.IO).catch { e: Throwable -> emit(Resource.Error("Unexpected Error ${e.message}")) }

    suspend fun createNote(title: String, content: String, createdAt: Date) {
        val note = Note(title = title, content = content, createdAt = createdAt)
        noteDao.insertNote(note)
        scheduleSyncWork()
    }

    suspend fun syncNotes() {
        try {
            // Get unsynced notes from local database
            val unsyncedNotes = noteDao.getUnsyncedNotes()

            // Sync each note with the server
            unsyncedNotes.forEach { note ->
                try {
                    apiService.createNote(note)
                    noteDao.updateNote(note.copy(isSynced = true))
                } catch (e: Exception) {
                    // Handle error
                    Log.d(
                        "NoteRepository", "Failed to sync note ${note.id}" +
                                " because of an error: ${e.message}"
                    )
                }
            }

            // Fetch latest notes from server
            val serverNotes = apiService.getNotes()
            serverNotes.forEach { note ->
                noteDao.insertNote(note.copy(isSynced = true))
            }
        } catch (e: Exception) {
            Log.d(
                "NoteRepository",
                "Failed to sync all notes because of an error: ${e.message}"
            )
        }
    }

    private fun scheduleSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWork = OneTimeWorkRequestBuilder<NoteSyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueue(syncWork)
    }
}
