package ru.martin.kotlin.ui.note

import android.arch.lifecycle.Observer
import ru.martin.kotlin.data.NotesRepository
import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.model.NoteResult
import ru.martin.kotlin.ui.base.BaseViewModel

class NoteViewModel(val repository: NotesRepository = NotesRepository)
    : BaseViewModel<Note?, NoteViewState>() {


    fun loadNote(noteid: String){
        repository.getNoteById(noteid).observeForever {result ->
            result ?: let { return@observeForever }

            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(result.data as? Note)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }    }

    fun save(note: Note){
        repository.saveNote(note)
    }

//    override fun onCleared() {
//        repository.getNoteById(noteId).removeObserver { noteObserver }
//    }
}