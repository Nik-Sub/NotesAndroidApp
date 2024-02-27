package com.example.notesapp.feature_note.data.repository

import com.example.notesapp.feature_note.data.dataSource.NoteDao
import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao : NoteDao
) : NoteRepository {
    override fun getAllNote(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNodeById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}