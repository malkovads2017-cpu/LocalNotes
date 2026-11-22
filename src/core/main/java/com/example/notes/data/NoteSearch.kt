package com.example.notes.data

object NoteSearch {
    fun filter(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) return notes
        return notes.filter {
            it.title.contains(query.trim(), ignoreCase = true) ||
                it.content.contains(query.trim(), ignoreCase = true)
        }
    }
}
