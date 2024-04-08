package com.imprarce.android.frencheducation.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnTaskClickListener

class TaskListAdapter(private val listTask : List<TaskListItem>, private val listener: OnTaskClickListener) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return TaskViewHolder(inflater)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = listTask[position]

        if (currentItem.isCorrectAnswer) {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_decided)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_not_decided)
        }

        holder.bind(currentItem, listener)
    }

    fun updateItem(position: Int, isCorrectAnswer: Boolean) {
        listTask[position].isCorrectAnswer = isCorrectAnswer
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = listTask.size

    @SuppressLint("SetTextI18n")
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val titleTask: TextView = itemView.findViewById(R.id.task_title)
        private val progressTask: TextView = itemView.findViewById(R.id.task_progress_text)

        fun bind(taskListItem: TaskListItem, listener: OnTaskClickListener) {
            titleTask.text = taskListItem.task.taskName
            progressTask.text = "0%"


            itemView.setOnClickListener {
                listener.onTaskClick(taskListItem)
            }
        }
    }

}