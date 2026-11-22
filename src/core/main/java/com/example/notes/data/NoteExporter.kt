package com.example.notes.data

import android.content.ContentResolver
import android.net.Uri
import java.io.IOException

class NoteExporter(private val resolver: ContentResolver) {
    @Throws(IOException::class)
    fun export(note: Note, destination: Uri) {
        val output = resolver.openOutputStream(destination)
            ?: throw IOException("Не удалось открыть файл для записи.")

        output.bufferedWriter(Charsets.UTF_8).use { writer ->
            writer.appendLine(note.title)
            writer.appendLine()
            writer.append(note.content)
        }
    }
}
