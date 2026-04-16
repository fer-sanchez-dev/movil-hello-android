package com.sanchez.helloandroid.ui.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sanchez.helloandroid.databinding.FragmentTaskDetailBinding
import com.sanchez.helloandroid.data.task.Task
import com.sanchez.helloandroid.data.task.TaskRepository

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val args: TaskDetailFragmentArgs by navArgs()

    private lateinit var taskRepository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskRepository = TaskRepository(requireContext())

        if (args.taskId != -1) {
            val task = taskRepository.getAllTasks().find { it.id == args.taskId }
            task?.let {
                binding.etTitle.setText(it.title)
                binding.etDescription.setText(it.description)
                binding.etReminderTime.setText(it.reminderTime)
                binding.checkBoxReminder.isChecked = it.hasReminder
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()

            if (title.isEmpty()) {
                binding.etTitle.error = "El título no puede estar vacío"
                return@setOnClickListener
            }

            val description = binding.etDescription.text.toString().trim()
            val hasReminder = binding.checkBoxReminder.isChecked

            val task = if (args.taskId == -1) {
                Task(
                    id = System.currentTimeMillis().toInt(),
                    title = title,
                    description = description,
                    reminderTime = binding.etReminderTime.text.toString().trim(),
                    hasReminder = hasReminder
                )
            } else {
                Task(
                    id = args.taskId,
                    title = title,
                    description = description,
                    reminderTime = binding.etReminderTime.text.toString().trim(),
                    hasReminder = hasReminder
                )
            }

            if (args.taskId == -1) {
                taskRepository.addTask(task)
            } else {
                taskRepository.updateTask(task)
            }

            if (hasReminder) {
                scheduleReminder(task)
            }

            findNavController().navigateUp()
        }
    }

    private fun scheduleReminder(task: Task) {
        val timeText = binding.etReminderTime.text.toString().trim()

        val triggerTime = if (timeText.matches(Regex("\\d{2}:\\d{2}"))) {
            val parts = timeText.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()

            val calendar = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, hour)
                set(java.util.Calendar.MINUTE, minute)
                set(java.util.Calendar.SECOND, 0)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(java.util.Calendar.DAY_OF_YEAR, 1)
                }
            }
            calendar.timeInMillis
        } else {
            System.currentTimeMillis() + 30_000
        }

        val intent = Intent(requireContext(), TaskReminderReceiver::class.java).apply {
            putExtra("task_title", task.title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Una sola declaración, se eliminó la duplicada
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}