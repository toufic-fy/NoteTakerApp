package com.example.notetakerapp.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile //This annotation ensures that changes to db_instance are immediately visible to all threads.
    //It prevents threads from caching the variable, ensuring the database instance is always up-to-date.
    private var db_instance: NoteDatabase? = null

    fun getDatabase(context: Context): NoteDatabase {
        return db_instance ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            ).build()
            db_instance = instance
            instance
        }
    }
}