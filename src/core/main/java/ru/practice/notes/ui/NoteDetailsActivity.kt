
package ru.practice.notes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.practice.notes.R
import ru.practice.notes.data.NotesDatabase
import ru.practice.notes.data.NoteRepository

class NoteDetailsActivity : AppCompatActivity() {
    private lateinit var repository: NoteRepository
    private var noteId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        repository = NoteRepository(NotesDatabase.getInstance(this).noteDao())
        noteId = intent.getLongExtra("note_id", -1L)
        loadNote()
    }

    private fun loadNote() {
        lifecycleScope.launch {
            val note = repository.get(noteId)
            if (note == null) { finish(); return@launch }
            findViewById<TextView>(R.id.detailTitle).text = note.title
            findViewById<TextView>(R.id.detailContent).text = note.content
            findViewById<Button>(R.id.editButton).setOnClickListener {
                startActivity(Intent(this@NoteDetailsActivity, EditNoteActivity::class.java)
                    .putExtra("note_id", note.id)
                    .putExtra("note_title", note.title)
                    .putExtra("note_content", note.content))
            }
            findViewById<Button>(R.id.deleteButton).setOnClickListener {
                AlertDialog.Builder(this@NoteDetailsActivity)
                    .setTitle("Удаление заметки")
                    .setMessage("Удалить заметку «${note.title}»?")
                    .setNegativeButton("Отмена", null)
                    .setPositiveButton("Удалить") { _, _ ->
                        lifecycleScope.launch { repository.delete(note.id); finish() }
                    }.show()
            }
        }
    }
}
