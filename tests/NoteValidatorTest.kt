package com.example.notes.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class NoteValidatorTest {

    @Test
    fun validTitle_passesValidation() {
        assertTrue(NoteValidator.validate("Покупки", "Молоко и хлеб").isSuccess)
    }

    @Test
    fun emptyTitle_failsValidation() {
        val result = NoteValidator.validate("   ", "Текст заметки")
        assertTrue(result.isFailure)
        assertEquals(
            "Введите заголовок заметки",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun titleLongerThan120Characters_failsValidation() {
        val result = NoteValidator.validate("А".repeat(121), "Текст")
        assertTrue(result.isFailure)
        assertEquals(
            "Заголовок не должен превышать 120 символов",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun titleWithExactly120Characters_passesValidation() {
        assertTrue(NoteValidator.validate("А".repeat(120), "").isSuccess)
    }
}
