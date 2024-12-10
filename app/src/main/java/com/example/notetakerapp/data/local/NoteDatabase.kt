package com.example.notetakerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notetakerapp.data.model.converters.DateTypeConverter
import com.example.notetakerapp.data.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}