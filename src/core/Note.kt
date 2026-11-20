package com.example.notes.core

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель заметки для локального хранения в Room.
 *
 * createdAt не меняется при редактировании.
 * updatedAt обновляется при каждом сохранении изменений.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
