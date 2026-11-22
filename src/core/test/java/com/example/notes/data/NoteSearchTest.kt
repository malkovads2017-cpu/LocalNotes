package com.example.notes.data

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteSearchTest {
    private val notes = listOf(
        Note(id = 1, title = "Покупки", content = "Молоко и хлеб"),
        Note(id = 2, title = "Учёба", content = "Практическая работа")
    )

    @Test fun findsByTitle() {
        assertEquals(listOf(1L), NoteSearch.filter(notes, "покупки").map { it.id })
    }

    @Test fun findsByContent() {
        assertEquals(listOf(2L), NoteSearch.filter(notes, "ПРАКТИЧЕСКАЯ").map { it.id })
    }

    @Test fun blankQueryReturnsAllNotes() {
        assertEquals(notes, NoteSearch.filter(notes, "  "))
    }

    @Test fun unmatchedQueryReturnsEmptyList() {
        assertEquals(emptyList<Note>(), NoteSearch.filter(notes, "нет совпадений"))
    }
}
