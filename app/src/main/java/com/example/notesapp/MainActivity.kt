package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.notesapp.feature_note.data.dataSource.NoteDatabase
import com.example.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.notesapp.feature_note.domain.useCase.AddNote
import com.example.notesapp.feature_note.domain.useCase.DeleteNote
import com.example.notesapp.feature_note.domain.useCase.GetNote
import com.example.notesapp.feature_note.domain.useCase.GetNotes
import com.example.notesapp.feature_note.domain.useCase.NoteUseCases
import com.example.notesapp.feature_note.presentation.addEditNote.AddEditNoteScreen
import com.example.notesapp.feature_note.presentation.addEditNote.AddEditNoteViewModel
import com.example.notesapp.feature_note.presentation.notes.NotesScreen
import com.example.notesapp.feature_note.presentation.notes.NotesViewModel
import com.example.notesapp.feature_note.presentation.util.Screen
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val db = databaseBuilder(
                        applicationContext,
                        NoteDatabase::class.java,
                        NoteDatabase.DATABASE_NAME
                    ).build()
                    val repository = NoteRepositoryImpl(db.noteDao)
                    val notesUseCases = NoteUseCases(
                        getNotes = GetNotes(repository),
                        deleteNote = DeleteNote(repository),
                        addNote = AddNote(repository),
                        getNote = GetNote(repository)
                    )
                    val notesViewModel = NotesViewModel(notesUseCases)
                    val addEditNoteViewModel = AddEditNoteViewModel(notesUseCases, navController)


                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController, notesViewModel)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color,
                                addEditNoteViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}