package com.example.notes.core

import android.content.ContentResolver
import android.net.Uri
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Экспортирует заметку в текстовый файл по Uri, полученному,
 * например, через ACTION_CREATE_DOCUMENT (Storage Access Framework).
 */
class NoteExporter(
    private val contentResolver: ContentResolver
) {

    @Throws(IOException::class)
    fun exportNote(note: Note, destination: Uri) {
        val text = buildString {
            appendLine(note.title)
            appendLine()
            appendLine(note.content)
        }

        val output = contentResolver.openOutputStream(destination)
            ?: throw IOException("Не удалось открыть файл для записи")

        output.use { stream ->
            stream.write(text.toByteArray(StandardCharsets.UTF_8))
            stream.flush()
        }
    }
}
