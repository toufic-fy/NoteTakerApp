package com.example.notetakerapp.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notetakerapp.data.local.DatabaseProvider
import com.example.notetakerapp.data.local.NoteDatabase
import com.example.notetakerapp.data.repository.NoteRepository

class NoteSyncWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private val repository: NoteRepository = createRepository(context)
    override suspend fun doWork(): Result {
        return try {
            repository.syncNotes()
            Result.success()
        }catch (e: Exception){
            Result.retry()
        }
    }

    private fun createRepository(context: Context): NoteRepository {
        val database = DatabaseProvider.getDatabase(context)
        val noteDao = database.noteDao()
        val apiService = RetrofitInstance.RetrofitInstance.noteApiService
        return NoteRepository(noteDao, apiService, context)
    }

}