package com.notesapp

import com.notesapp.core.NoteExporter
import com.notesapp.core.NoteItem
import com.notesapp.core.NoteValidator
import org.junit.Assert.*
import org.junit.Test

/**
 * Модульные тесты ядра мобильного приложения «Заметки» (День 7).
 * Автор: Малкова Диана Сергеевна
 */
class NoteUnitTest {

    @Test
    fun validate_emptyTitleAndContent_returnsFalse() {
        val result = NoteValidator.validate("   ", "")
        assertFalse(result.isValid)
        assertEquals("Заметка не может быть полностью пустой", result.errorMessage)
    }

    @Test
    fun validate_validNote_returnsTrue() {
        val result = NoteValidator.validate("Список покупок", "Молоко, хлеб, кофе")
        assertTrue(result.isValid)
        assertNull(result.errorMessage)
    }

    @Test
    fun validate_tooLongTitle_returnsFalse() {
        val longTitle = "А".repeat(101)
        val result = NoteValidator.validate(longTitle, "Текст")
        assertFalse(result.isValid)
    }

    @Test
    fun filterNotes_caseInsensitiveSearch_findsMatches() {
        val notes = listOf(
            NoteItem(1, "Покупки", "Купить яблоки и сыр", 1700000000000L),
            NoteItem(2, "Учёба", "Сдать отчёт по Kotlin", 1700000005000L),
            NoteItem(3, "Идеи", "Мобильное приложение Заметки", 1700000010000L)
        )
        val found = NoteExporter.filterNotes(notes, "kotlin")
        assertEquals(1, found.size)
        assertEquals(2L, found.first().id)
    }

    @Test
    fun sanitizeFileName_removesIllegalChars() {
        val fileName = NoteExporter.sanitizeFileName("Отчёт: 2026/11*?")
        assertEquals("Отчёт_ 2026_11__.txt", fileName)
    }

    @Test
    fun toPlainText_blankTitle_usesDefaultTitle() {
        val note = NoteItem(1, "", "Текст без заголовка", 1700000000000L)
        val exported = NoteExporter.toPlainText(note)
        assertTrue(exported.contains("Заголовок: Без названия"))
        assertTrue(exported.contains("Текст без заголовка"))
    }
}
