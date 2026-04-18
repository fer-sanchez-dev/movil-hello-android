package com.sanchez.helloandroid.ui.task

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanchez.helloandroid.databinding.ItemTaskBinding
import com.sanchez.helloandroid.data.task.Task

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onTaskClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.binding.tvTaskTitle.text = task.title
        holder.binding.tvTaskDescription.text = task.description

        // Reflejar estado del checkbox sin disparar el listener
        holder.binding.checkBoxDone.setOnCheckedChangeListener(null)
        holder.binding.checkBoxDone.isChecked = task.isDone

        // Tachar el título si la tarea está completada
        if (task.isDone) {
            holder.binding.tvTaskTitle.paintFlags =
                holder.binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.tvTaskTitle.alpha = 0.5f
        } else {
            holder.binding.tvTaskTitle.paintFlags =
                holder.binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.tvTaskTitle.alpha = 1f
        }

        // Al marcar el checkbox, actualizar el estado visualmente
        holder.binding.checkBoxDone.setOnCheckedChangeListener { _, isChecked ->
            tasks[position] = task.copy(isDone = isChecked)
            notifyItemChanged(position)
        }

        // Al tocar la tarjeta, abrir el detalle
        holder.itemView.setOnClickListener { onTaskClick(task) }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}