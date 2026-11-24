
package ru.practice.notes.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var db: NotesDatabase
    private lateinit var dao: NoteDao

    @Before fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java).allowMainThreadQueries().build()
        dao = db.noteDao()
    }
    @After fun closeDb() = db.close()

    @Test fun insertReadUpdateDelete() = runBlocking {
        val id = dao.insert(Note(title = "План", content = "Подготовить отчёт"))
        assertEquals("План", dao.getById(id)?.title)
        dao.update(Note(id = id, title = "План на день", content = "Подготовить отчёт"))
        assertEquals("План на день", dao.getById(id)?.title)
        dao.deleteById(id)
        assertNull(dao.getById(id))
    }

    @Test fun searchFindsTitleAndContent() = runBlocking {
        dao.insert(Note(title = "Покупки", content = "Молоко и хлеб"))
        assertEquals(1, dao.search("Молоко").size)
        assertEquals(1, dao.search("Покуп").size)
    }
}
