package ru.martin.kotlin.data

import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.data.provider.FireStoreProvider
import ru.martin.kotlin.data.provider.RemoteDataProvider


object NotesRepository {
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

}