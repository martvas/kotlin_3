package ru.martin.kotlin.data.provider

import android.arch.lifecycle.LiveData
import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun getNoteById(id : String) : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>

}