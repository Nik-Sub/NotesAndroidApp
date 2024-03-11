package com.example.notesapp.feature_note.domain.repository

import com.example.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {


    fun getAllNote() : Flow<List<Note>>
    suspend fun getNodeById(id : Int) : Note?
    suspend fun insertNote(note : Note)
    suspend fun deleteNote(note : Note)

}