package ru.martin.kotlin.ui.main

import android.arch.lifecycle.Observer
import ru.martin.kotlin.data.NotesRepository
import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.model.NoteResult
import ru.martin.kotlin.ui.base.BaseViewModel

class MainViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: let { return@Observer }

        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(result.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever { notesObserver }
    }

    override fun onCleared() {
        repositoryNotes.removeObserver { notesObserver }
    }
}