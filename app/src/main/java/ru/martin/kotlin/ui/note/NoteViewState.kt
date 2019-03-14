package ru.martin.kotlin.ui.note

import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null)
    : BaseViewState<Note?>(note, error)