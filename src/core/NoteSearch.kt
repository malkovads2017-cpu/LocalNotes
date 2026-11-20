package com.example.notes.core

/**
 * Вспомогательный поиск по уже загруженному списку заметок.
 * Для больших объёмов данных предпочтительно использовать NoteDao.search().
 */
object NoteSearch {

    fun filterNotes(notes: List<Note>, query: String): List<Note> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) return notes

        return notes.filter { note ->
            note.title.contains(normalizedQuery, ignoreCase = true) ||
                note.content.contains(normalizedQuery, ignoreCase = true)
        }
    }
}
