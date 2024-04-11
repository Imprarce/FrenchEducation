package com.imprarce.android.frencheducation.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnTaskClickListener

class TaskListAdapter(
    private val listTask : List<TaskListItem>,
    private val listener: OnTaskClickListener,
    private val listCompletedTasks : List<Int>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var completedPositions = listCompletedTasks.toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return TaskViewHolder(inflater)
    }

    fun markTaskCompleted(taskId: Int) {
        completedPositions.add(taskId)
        notifyItemChanged(taskId)

    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = listTask[position]

        val isTaskCompleted = completedPositions.contains(currentItem.task.id_task)

        val constraintLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutTask)

        if (isTaskCompleted) {
            constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_decided)
        } else {
            constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_recycler_view_task_not_decided)
        }

        holder.titleTask.text = currentItem.task.taskName
        val progressText = if (isTaskCompleted) "100%" else "0%"
        holder.progressTask.text = progressText

        holder.itemView.setOnClickListener {
            listener.onTaskClick(currentItem)
        }
    }

    override fun getItemCount(): Int = listTask.size


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTask: TextView = itemView.findViewById(R.id.task_title)
        val progressTask: TextView = itemView.findViewById(R.id.task_progress_text)
    }
}