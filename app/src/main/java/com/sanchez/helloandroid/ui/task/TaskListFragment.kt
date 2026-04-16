package com.sanchez.helloandroid.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanchez.helloandroid.R
import com.sanchez.helloandroid.databinding.FragmentTaskListBinding
import com.sanchez.helloandroid.data.task.Task
import com.sanchez.helloandroid.data.task.TaskRepository

class TaskListFragment : Fragment() {

    // ViewBinding (igual que en Taller 2)
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    // Repositorio de tareas (el de la Parte 5 del taller)
    private lateinit var taskRepository: TaskRepository

    // Lista y adapter del RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar repositorio
        taskRepository = TaskRepository(requireContext())

        // Configurar RecyclerView
        taskAdapter = TaskAdapter(
            tasks = taskRepository.getAllTasks().toMutableList(),
            onTaskClick = { task ->
                // Al tocar una tarea: ir al detalle en modo edición
                // Aquí pasaremos el id de la tarea como argumento de navegación
                val action = TaskListFragmentDirections
                    .actionTaskListToTaskDetail(taskId = task.id)
                findNavController().navigate(action)
            }
        )

        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        // Botón flotante: ir a crear nueva tarea (id = -1 significa nueva)
        binding.fabAddTask.setOnClickListener {
            val action = TaskListFragmentDirections
                .actionTaskListToTaskDetail(taskId = -1)
            findNavController().navigate(action)
        }
    }

    // Se llama cada vez que vuelves a este fragment (al guardar una tarea)
    override fun onResume() {
        super.onResume()
        // Recargar la lista para reflejar cambios
        taskAdapter.updateTasks(taskRepository.getAllTasks())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}