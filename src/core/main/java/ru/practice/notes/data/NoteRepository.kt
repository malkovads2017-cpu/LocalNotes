
package ru.practice.notes.data

class NoteRepository(private val dao: NoteDao) {
    suspend fun all(): List<Note> = dao.getAll()
    suspend fun find(query: String): List<Note> =
        if (query.isBlank()) dao.getAll() else dao.search(query.trim())
    suspend fun get(id: Long): Note? = dao.getById(id)

    suspend fun save(id: Long?, title: String, content: String): Result<Long> {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty()) return Result.failure(IllegalArgumentException("Введите название заметки."))
        if (content.isBlank()) return Result.failure(IllegalArgumentException("Введите текст заметки."))
        return runCatching {
            val note = Note(id = id ?: 0, title = cleanTitle, content = content.trim())
            if (id == null) dao.insert(note) else {
                dao.update(note)
                id
            }
        }
    }
    suspend fun delete(id: Long) = dao.deleteById(id)
}
