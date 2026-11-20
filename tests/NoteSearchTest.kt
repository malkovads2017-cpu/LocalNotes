package com.example.notes.core

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteSearchTest {

    private val notes = listOf(
        Note(id = 1, title = "Покупки", content = "Молоко и хлеб"),
        Note(id = 2, title = "Учёба", content = "Подготовить практическую работу"),
        Note(id = 3, title = "Идеи", content = "Приложение для заметок")
    )

    @Test
    fun searchByTitle_findsMatchingNote() {
        val result = NoteSearch.filterNotes(notes, "покупки")
        assertEquals(listOf(1L), result.map { it.id })
    }

    @Test
    fun searchByContent_findsMatchingNote() {
        val result = NoteSearch.filterNotes(notes, "практическую")
        assertEquals(listOf(2L), result.map { it.id })
    }

    @Test
    fun searchIsCaseInsensitive() {
        val result = NoteSearch.filterNotes(notes, "УЧЁБА")
        assertEquals(listOf(2L), result.map { it.id })
    }

    @Test
    fun emptyQuery_returnsAllNotes() {
        assertEquals(notes, NoteSearch.filterNotes(notes, "   "))
    }

    @Test
    fun unmatchedQuery_returnsEmptyList() {
        assertTrueEmpty(NoteSearch.filterNotes(notes, "несуществующее"))
    }

    private fun assertTrueEmpty(result: List<Note>) {
        assertEquals(emptyList<Note>(), result)
    }
}
