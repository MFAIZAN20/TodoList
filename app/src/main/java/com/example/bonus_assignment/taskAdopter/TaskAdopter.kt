package com.example.bonus_assignment.taskAdopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bonus_assignment.R
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bonus_assignment.taskModel.TaskResponse

class TaskAdapter(
    private val taskResponseList: MutableList<TaskResponse>,
    private val onDeleteClick: (TaskResponse) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        val taskDescription: TextView = itemView.findViewById(R.id.tv_task_description)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskResponseList[position]
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description

        holder.deleteButton.setOnClickListener {
            onDeleteClick(task)
        }
    }

    override fun getItemCount(): Int {
        return taskResponseList.size
    }
}
