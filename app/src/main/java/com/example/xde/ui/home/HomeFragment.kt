package com.example.xde.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xde.R
import android.app.Dialog
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.xde.Utility.generateRandomId



class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList: MutableList<Task> = mutableListOf()
    val inProgressTaskList: MutableList<Task> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val fabAddTask = root.findViewById<FloatingActionButton>(R.id.fab_add_task)

        fabAddTask.setOnClickListener {
            // Görev ekleme dialog
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_task_dialog) // Özel tasarım dosyasını kullanın

            // Görev başlığı açıklama alanları
            val taskTitleEditText = dialog.findViewById<EditText>(R.id.editTextTaskTitle)
            val taskDescriptionEditText = dialog.findViewById<EditText>(R.id.editTextTaskDescription)

            val addTaskButton = dialog.findViewById<Button>(R.id.button_add_task)
            addTaskButton.setOnClickListener {
                // Kullanıcının girdiği görev bilgilerini alma
                val taskTitle = taskTitleEditText.text.toString()
                val taskDescription = taskDescriptionEditText.text.toString()

                //yeni görevi ekle
                val newTask = Task(id = generateRandomId(), title = taskTitle, description = taskDescription, status = TaskStatus.TO_DO)
                taskList.add(newTask) // Örnek olarak taskList'e ekliyoruz, sizin kullanacağınız veri yapısına göre farklılık gösterebilir.

                taskAdapter.notifyDataSetChanged()

                dialog.dismiss()
            }

            dialog.show()
        }


        recyclerView = root.findViewById(R.id.recycler_view_tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // TaskAdapter'ı oluştururken görev listesini sağlama
        taskAdapter = TaskAdapter(taskList)

        taskAdapter.setOnItemClickListener{task: Task ->
            // Task item'ına tıklandığında
            showTaskDetailsDialog(task)
        }
        recyclerView.adapter = taskAdapter

        return root
    }

    private fun updateInProgressList(task: Task) {
        inProgressTaskList.add(task)
        taskAdapter.notifyDataSetChanged()
    }

    private fun startTask(taskId: String) {
        val taskToStart = taskList.find { it.id == taskId }
        taskToStart?.let {
            it.status = TaskStatus.IN_PROGRESS
            updateInProgressList(it)
            taskList.remove(it)
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun removeTask(taskId: String) {
        val taskToRemove = taskList.find { it.id == taskId }
        taskToRemove?.let {
            taskList.remove(it)
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun showTaskDetailsDialog(task: Task) {
        // Task diyaloğu
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(task.title)
            .setMessage(task.description)
            .setPositiveButton("Başlat") { dialog, which ->
               startTask(task.id)
            }
            .setNegativeButton("Sil") { _, _ ->
                removeTask(task.id)
                Toast.makeText(requireContext(), "Task silindi.", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Kapat") { _, _ ->
                // Diyalog penceresini kapat
            }
            .create()

        dialog.show()
    }

}
