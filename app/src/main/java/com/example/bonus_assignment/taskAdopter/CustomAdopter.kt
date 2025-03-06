package com.example.bonus_assignment.taskAdopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.bonus_assignment.R
import com.example.bonus_assignment.taskModel.TaskResponse

class CustomAdapter(
    context: Context,
    private val taskResponseList: MutableList<TaskResponse>,
    private val onDeleteClick: (Int) -> Unit // A callback for delete action
) : ArrayAdapter<TaskResponse>(context, R.layout.task_item, taskResponseList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        val taskTitleTextView = view.findViewById<TextView>(R.id.tv_task_title)
        val taskDescriptionTextView = view.findViewById<TextView>(R.id.tv_task_description)
        val deleteButton = view.findViewById<Button>(R.id.btn_delete_task)

        val task = taskResponseList[position]
        taskTitleTextView.text = task.title
        taskDescriptionTextView.text = task.description

        // Set up the delete button functionality
        deleteButton.setOnClickListener {
            // Trigger the delete callback
            onDeleteClick(position)
        }
        return view
    }
}
