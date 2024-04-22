package com.imprarce.android.feature_home.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.local.dictionary.DictionaryListItem

class DictionaryAdapter (private val dictionaryItem: List<DictionaryListItem>) : RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dictionary_recycler_view, parent, false)
        return DictionaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val currentItem = dictionaryItem[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dictionaryItem.size
    }

    class DictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val levelTextView: TextView = itemView.findViewById(R.id.level_dictionary)
        private val wordFrTextView: TextView = itemView.findViewById(R.id.word_fr)
        private val wordRusTextView: TextView = itemView.findViewById(R.id.word_rus)

        @SuppressLint("SetTextI18n")
        fun bind(dictionaryItem: DictionaryListItem) {
            levelTextView.text = "A" + dictionaryItem.dictionary.level.toString()
            wordFrTextView.text = dictionaryItem.dictionary.wordFr
            wordRusTextView.text = dictionaryItem.dictionary.wordRu
        }
    }
}