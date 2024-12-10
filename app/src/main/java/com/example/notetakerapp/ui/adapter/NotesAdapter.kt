package com.example.notetakerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notetakerapp.R
import com.example.notetakerapp.data.model.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NotesAdapter(): ListAdapter<Note, NotesAdapter.NotesViewHolder>(NoteDiffCallback()) {
    private val displayFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
    inner class NotesViewHolder(itemView: View): ViewHolder(itemView){
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val contentTv: TextView = itemView.findViewById(R.id.contentTv)
        val dateTv: TextView = itemView.findViewById(R.id.dateTv)
    }

    class NoteDiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        holder.titleTv.text = note.title
        holder.contentTv.text = note.content
        val formattedDate = displayFormat.format(note.createdAt)
        holder.dateTv.text = formattedDate
    }
}