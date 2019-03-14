package ru.martin.kotlin.ui.main

import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null, error: Throwable? = null)
    : BaseViewState<List<Note>?>(notes, error)