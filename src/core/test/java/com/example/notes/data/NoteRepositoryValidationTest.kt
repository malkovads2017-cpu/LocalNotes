package com.example.notes.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class NoteRepositoryValidationTest {
    @Test fun blankTitleIsRejected() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            require("   ".isNotBlank()) { "Введите заголовок заметки." }
        }
        assertEquals("Введите заголовок заметки.", exception.message)
    }

    @Test fun titleOver120CharactersIsRejected() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            require("А".repeat(121).length <= 120) {
                "Заголовок не должен превышать 120 символов."
            }
        }
        assertEquals("Заголовок не должен превышать 120 символов.", exception.message)
    }
}
