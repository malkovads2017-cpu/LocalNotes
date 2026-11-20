package com.example.notes.core

object NoteValidator {

    /**
     * Заголовок обязателен. Содержимое может быть пустым,
     * если пользователь хочет сохранить заметку только с названием.
     */
    fun validate(title: String, content: String): Result<Unit> {
        if (title.trim().isEmpty()) {
            return Result.failure(
                IllegalArgumentException("Введите заголовок заметки")
            )
        }

        if (title.trim().length > 120) {
            return Result.failure(
                IllegalArgumentException("Заголовок не должен превышать 120 символов")
            )
        }

        return Result.success(Unit)
    }
}
