package com.example.notetakerapp.data.model

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.example.notetakerapp.data.local.NoteDao


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val title: String,
    val content: String )

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}
