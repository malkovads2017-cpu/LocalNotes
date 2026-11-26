package com.notesapp.core

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object NoteValidator {
    const val MAX_TITLE_LENGTH = 100
    const val MAX_CONTENT_LENGTH = 10000

    fun validate(title: String, content: String): ValidationResult {
        val trimmedTitle = title.trim()
        val trimmedContent = content.trim()

        if (trimmedTitle.isEmpty() && trimmedContent.isEmpty()) {
            return ValidationResult(false, "Заметка не может быть полностью пустой")
        }
        if (trimmedTitle.length > MAX_TITLE_LENGTH) {
            return ValidationResult(false, "Заголовок не должен превышать $MAX_TITLE_LENGTH символов")
        }
        if (trimmedContent.length > MAX_CONTENT_LENGTH) {
            return ValidationResult(false, "Текст заметки не должен превышать $MAX_CONTENT_LENGTH символов")
        }
        return ValidationResult(true)
    }
}
