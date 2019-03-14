package ru.martin.kotlin.ui.main

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.geekbrains.kotlin_2.R
import ru.martin.kotlin.data.entity.Note

class NotesRVAdapter(val onItemClick : ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titleTextVew = itemView.findViewById<TextView>(R.id.tv_title)
        val textTextVew = itemView.findViewById<TextView>(R.id.tv_text)

        fun bind(note: Note) = with(note) {
            titleTextVew.text = title
            textTextVew.text = text

            val background = when(color){
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, background))
            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }

    }
}