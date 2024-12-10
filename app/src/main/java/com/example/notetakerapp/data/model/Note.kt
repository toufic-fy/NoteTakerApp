package com.example.notetakerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.notetakerapp.data.model.converters.DateTypeConverter
import java.util.Date


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title: String,
    val content: String,
    @TypeConverters(DateTypeConverter::class)
    val createdAt: Date,
    val isSynced: Boolean = false
)
