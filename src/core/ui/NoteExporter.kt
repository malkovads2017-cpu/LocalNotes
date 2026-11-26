package com.notesapp.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NoteItem(
    val id: Long,
    val title: String,
    val content: String,
    val updatedAt: Long
)

object NoteExporter {
    fun toPlainText(note: NoteItem): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val dateStr = formatter.format(Date(note.updatedAt))
        val safeTitle = note.title.ifBlank { "Без названия" }
        return buildString {
            appendLine("Заголовок: $safeTitle")
            appendLine("Дата изменения: $dateStr")
            appendLine("---")
            append(note.content)
        }
    }

    fun sanitizeFileName(title: String): String {
        val cleaned = title.trim().replace(Regex("[\\/:*?"<>|]"), "_")
        return if (cleaned.isBlank()) "note_export.txt" else "${cleaned.take(40)}.txt"
    }

    fun filterNotes(notes: List<NoteItem>, query: String): List<NoteItem> {
        val q = query.trim().lowercase(Locale.getDefault())
        if (q.isEmpty()) return notes
        return notes.filter {
            it.title.lowercase(Locale.getDefault()).contains(q) ||
                it.content.lowercase(Locale.getDefault()).contains(q)
        }
    }
}
