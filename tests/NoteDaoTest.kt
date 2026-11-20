package com.example.notes.core

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var database: NotesDatabase
    private lateinit var dao: NoteDao

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.noteDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAndReadNote() = runBlocking {
        val note = Note(title = "Покупки", content = "Молоко")
        val id = dao.insert(note)

        val saved = dao.getById(id)

        assertEquals("Покупки", saved?.title)
        assertEquals("Молоко", saved?.content)
    }

    @Test
    fun updateNote_changesStoredValues() = runBlocking {
        val id = dao.insert(Note(title = "Черновик", content = "Старый текст"))
        val saved = dao.getById(id)!!

        dao.update(saved.copy(title = "План", content = "Новый текст"))

        val updated = dao.getById(id)
        assertEquals("План", updated?.title)
        assertEquals("Новый текст", updated?.content)
    }

    @Test
    fun deleteNote_removesRecord() = runBlocking {
        val id = dao.insert(Note(title = "Удалить", content = "Текст"))

        dao.deleteById(id)

        assertNull(dao.getById(id))
    }

    @Test
    fun searchFindsTitleAndContentMatches() = runBlocking {
        dao.insert(Note(title = "Покупки", content = "Молоко"))
        dao.insert(Note(title = "Учёба", content = "Практическая работа"))

        val titleMatches = dao.search("Покупки").first()
        val contentMatches = dao.search("Практическая").first()

        assertEquals(1, titleMatches.size)
        assertEquals("Покупки", titleMatches.first().title)
        assertEquals(1, contentMatches.size)
        assertEquals("Учёба", contentMatches.first().title)
    }

    @Test
    fun observeAll_returnsNotesOrderedByUpdatedAtDescending() = runBlocking {
        dao.insert(Note(title = "Старая", content = "", updatedAt = 100L))
        dao.insert(Note(title = "Новая", content = "", updatedAt = 200L))

        val result = dao.observeAll().first()

        assertEquals(listOf("Новая", "Старая"), result.map { it.title })
    }
}
