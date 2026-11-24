
package ru.practice.notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practice.notes.R
import ru.practice.notes.data.Note

class NoteAdapter(private val onClick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private val items = mutableListOf<Note>()

    fun submitItems(notes: List<Note>) {
        items.clear()
        items.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = items[position]
        holder.title.text = note.title
        holder.preview.text = note.content.take(90)
        holder.itemView.setOnClickListener { onClick(note) }
    }

    override fun getItemCount(): Int = items.size

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.noteTitle)
        val preview: TextView = view.findViewById(R.id.notePreview)
    }
}
