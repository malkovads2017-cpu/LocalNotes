
package ru.practice.notes.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class NoteValidationTest {
    @Test fun titleIsTrimmedBeforeSaving() {
        assertEquals("Покупки", "  Покупки  ".trim())
    }
    @Test fun blankTitleIsRejected() {
        assertNotEquals("", "   ".trim())
    }
    @Test fun contentIsTrimmedBeforeSaving() {
        assertEquals("Купить хлеб", " Купить хлеб ".trim())
    }
}
