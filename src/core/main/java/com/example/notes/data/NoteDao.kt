package com.example.notes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<Note>>

    @Query("""
        SELECT * FROM notes
        WHERE title LIKE '%' || :query || '%'
           OR content LIKE '%' || :query || '%'
        ORDER BY updatedAt DESC
    """)
    fun search(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Note?

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
