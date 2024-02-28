package com.example.notesapp.feature_note.domain.useCase

import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note : Note){
        repository.insertNote(note)
    }
}