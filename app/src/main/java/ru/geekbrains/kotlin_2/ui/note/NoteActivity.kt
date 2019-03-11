package ru.geekbrains.kotlin_2.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.kotlin_2.R
import ru.geekbrains.kotlin_2.data.entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var note: Note? = null
    lateinit var viewModel: NoteViewModel
    private var spinnerColorId = 0

    companion object {

        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L

        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    val textChangeWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkIsTitleNotEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView()
        btn_save_note.setOnClickListener { saveNote()}
    }

    private fun initView() {
        ArrayAdapter.createFromResource(this, R.array.note_colors, android.R.layout.simple_spinner_item)
            .also {adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_color.adapter = adapter }
        spinner_color.onItemSelectedListener = this

        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)
            spinner_color.setSelection(it.color.id)
            val background = when (it.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }
            toolbar.setBackgroundColor(ContextCompat.getColor(this, background))
        }

        et_title.addTextChangedListener(textChangeWatcher)
    }

    private fun saveNote() {
        if (checkIsTitleNotEmpty()){
            note = note?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChanged = Date(),
                color = getColorById() ?: note?.color ?: Note.Color.WHITE
            ) ?: Note(
                UUID.randomUUID().toString(),
                et_title.text.toString(),
                et_body.text.toString(),
                getColorById() ?: Note.Color.WHITE
            )

            note?.let { viewModel.save(note!!) }
            onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun checkIsTitleNotEmpty() : Boolean {
        return if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) {
            til_title.error = "Заполните поле. Минимум 3 символа."
            false
        } else {
            til_title.error = ""
            true
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.spinnerColorId = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getColorById() : Note.Color? {
        enumValues<Note.Color>().forEach { if (spinnerColorId == it.id) return it }
        return null
    }

}