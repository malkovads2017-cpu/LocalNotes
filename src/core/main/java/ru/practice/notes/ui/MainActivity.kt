
package ru.practice.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.practice.notes.R
import ru.practice.notes.data.NotesDatabase
import ru.practice.notes.data.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var repository: NoteRepository
    private lateinit var adapter: NoteAdapter
    private lateinit var emptyText: TextView
    private lateinit var searchField: EditText
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository = NoteRepository(NotesDatabase.getInstance(this).noteDao())
        emptyText = findViewById(R.id.emptyText)
        searchField = findViewById(R.id.searchField)
        adapter = NoteAdapter { note ->
            startActivity(Intent(this, NoteDetailsActivity::class.java).putExtra("note_id", note.id))
        }
        findViewById<RecyclerView>(R.id.notesList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        findViewById<FloatingActionButton>(R.id.addNoteButton).setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
        }
        searchField.addTextChangedListener(SimpleTextWatcher {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(180)
                loadNotes(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (::repository.isInitialized) loadNotes(searchField.text.toString())
    }

    private fun loadNotes(query: String) {
        lifecycleScope.launch {
            runCatching { repository.find(query) }
                .onSuccess { notes ->
                    adapter.submitItems(notes)
                    emptyText.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
                    emptyText.text = if (query.isBlank())
                        "Заметок пока нет. Нажмите «Добавить», чтобы создать первую."
                    else "По запросу ничего не найдено."
                }
                .onFailure { emptyText.visibility = View.VISIBLE; emptyText.text = "Не удалось загрузить заметки." }
        }
    }
}
