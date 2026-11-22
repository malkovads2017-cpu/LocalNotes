package com.example.notes.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {
    fun observeNotes(): Flow<List<Note>> = dao.observeAll()

    fun searchNotes(query: String): Flow<List<Note>> =
        if (query.isBlank()) dao.observeAll() else dao.search(query.trim())

    suspend fun getNote(id: Long): Note? = dao.getById(id)

    suspend fun create(title: String, content: String): Long {
        require(title.isNotBlank()) { "Введите заголовок заметки." }
        require(title.trim().length <= 120) {
            "Заголовок не должен превышать 120 символов."
        }
        val now = System.currentTimeMillis()
        return dao.insert(
            Note(title = title.trim(), content = content, createdAt = now, updatedAt = now)
        )
    }

    suspend fun update(id: Long, title: String, content: String) {
        require(title.isNotBlank()) { "Введите заголовок заметки." }
        require(title.trim().length <= 120) {
            "Заголовок не должен превышать 120 символов."
        }
        val old = dao.getById(id) ?: throw NoSuchElementException("Заметка не найдена.")
        dao.update(old.copy(
            title = title.trim(),
            content = content,
            updatedAt = System.currentTimeMillis()
        ))
    }

    suspend fun delete(id: Long) {
        if (dao.deleteById(id) == 0) {
            throw NoSuchElementException("Заметка уже удалена.")
        }
    }
}
