package ru.martin.kotlin.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.kotlin_2.R
import ru.martin.kotlin.data.entity.Note
import ru.martin.kotlin.ui.note.NoteActivity
import ru.martin.kotlin.ui.base.BaseActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
                ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_main

    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter {
            NoteActivity.start(this, it.id)
        }

        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }

    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}
