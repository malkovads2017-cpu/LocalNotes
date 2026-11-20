package com.example.notes.core

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий — единая точка доступа к заметкам.
 * Интерфейс пользовательского экрана должен обращаться к нему,
 * а не к Room DAO напрямую.
 */
class NoteRepository(
    private val noteDao: NoteDao
) {
    fun observeNotes(): Flow<List<Note>> = noteDao.observeAll()

    fun searchNotes(query: String): Flow<List<Note>> {
        val normalizedQuery = query.trim()
        return if (normalizedQuery.isEmpty()) {
            noteDao.observeAll()
        } else {
            noteDao.search(normalizedQuery)
        }
    }

    suspend fun getNote(noteId: Long): Note? = noteDao.getById(noteId)

    suspend fun createNote(title: String, content: String): Result<Long> {
        val validation = NoteValidator.validate(title, content)
        if (validation.isFailure) {
            return Result.failure(validation.exceptionOrNull()!!)
        }

        val now = System.currentTimeMillis()
        val note = Note(
            title = title.trim(),
            content = content,
            createdAt = now,
            updatedAt = now
        )
        return runCatching { noteDao.insert(note) }
    }

    suspend fun updateNote(
        noteId: Long,
        title: String,
        content: String
    ): Result<Unit> {
        val validation = NoteValidator.validate(title, content)
        if (validation.isFailure) {
            return Result.failure(validation.exceptionOrNull()!!)
        }

        return runCatching {
            val existing = noteDao.getById(noteId)
                ?: throw NoSuchElementException("Заметка не найдена")

            noteDao.update(
                existing.copy(
                    title = title.trim(),
                    content = content,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun deleteNote(noteId: Long) {
        noteDao.deleteById(noteId)
    }
}
