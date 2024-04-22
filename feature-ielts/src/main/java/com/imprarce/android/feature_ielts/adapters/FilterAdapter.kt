package com.imprarce.android.feature_ielts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.feature_ielts.R

class FilterAdapter(private val filterItems: List<Int>) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filterText = holder.itemView.context.getString(filterItems[position])
        holder.bind(filterText)
    }

    override fun getItemCount(): Int {
        return filterItems.size
    }

    class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filterTextView: TextView = itemView.findViewById(R.id.textView)

        fun bind(filterText: String) {
            filterTextView.text = filterText
        }
    }
}
