
package ru.practice.notes.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.practice.notes.R
import ru.practice.notes.data.NotesDatabase
import ru.practice.notes.data.NoteRepository

class EditNoteActivity : AppCompatActivity() {
    private lateinit var repository: NoteRepository
    private var noteId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        repository = NoteRepository(NotesDatabase.getInstance(this).noteDao())
        noteId = intent.getLongExtra("note_id", -1L).takeIf { it > 0 }
        val title = findViewById<EditText>(R.id.titleInput)
        val content = findViewById<EditText>(R.id.contentInput)
        if (noteId != null) {
            title.setText(intent.getStringExtra("note_title").orEmpty())
            content.setText(intent.getStringExtra("note_content").orEmpty())
        }
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            lifecycleScope.launch {
                repository.save(noteId, title.text.toString(), content.text.toString())
                    .onSuccess { finish() }
                    .onFailure { Toast.makeText(this@EditNoteActivity, it.message, Toast.LENGTH_SHORT).show() }
            }
        }
        findViewById<Button>(R.id.cancelButton).setOnClickListener { finish() }
    }
}
