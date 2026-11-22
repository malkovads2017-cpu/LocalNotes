package com.example.notes.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var database: NotesDatabase
    private lateinit var dao: NoteDao

    @Before fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.noteDao()
    }

    @After fun tearDown() = database.close()

    @Test fun insertReadUpdateDeleteNote() = runBlocking {
        val id = dao.insert(Note(title = "Черновик", content = "Текст"))
        val inserted = dao.getById(id)
        assertEquals("Черновик", inserted?.title)

        dao.update(inserted!!.copy(title = "План", content = "Новый текст"))
        assertEquals("План", dao.getById(id)?.title)

        dao.deleteById(id)
        assertNull(dao.getById(id))
    }

    @Test fun searchLooksInTitleAndContent() = runBlocking {
        dao.insert(Note(title = "Покупки", content = "Молоко"))
        dao.insert(Note(title = "Учёба", content = "Практическая работа"))

        assertEquals(1, dao.search("Покупки").first().size)
        assertEquals(1, dao.search("Практическая").first().size)
    }
}
