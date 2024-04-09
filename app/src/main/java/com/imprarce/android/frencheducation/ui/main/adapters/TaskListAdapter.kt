package com.imprarce.android.frencheducation.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnTaskClickListener

class TaskListAdapter(private val listTask : List<TaskListItem>, private val listener: OnTaskClickListener,
private val listCompletedTasks : List<Int>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return TaskViewHolder(inflater)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = listTask[position]

        val isTaskCompleted = listCompletedTasks.contains(currentItem.task.id_task)

        val linearLayout = holder.itemView.findViewById<LinearLayout>(R.id.linearLayout)

        if (isTaskCompleted) {
            linearLayout.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_decided)
        } else {
            linearLayout.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_not_decided)
        }

        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int = listTask.size

    @SuppressLint("SetTextI18n")
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val titleTask: TextView = itemView.findViewById(R.id.task_title)
        private val progressTask: TextView = itemView.findViewById(R.id.task_progress_text)

        fun bind(taskListItem: TaskListItem, listener: OnTaskClickListener) {
            titleTask.text = taskListItem.task.taskName
            val isTaskCompleted = listCompletedTasks.contains(taskListItem.task.id_task)
            progressTask.text = if (isTaskCompleted) "100%" else "0%"


            itemView.setOnClickListener {
                listener.onTaskClick(taskListItem)
            }
        }
    }

}