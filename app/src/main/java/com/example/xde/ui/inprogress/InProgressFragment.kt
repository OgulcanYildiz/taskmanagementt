package com.example.xde.ui.inprogress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xde.R
import com.example.xde.ui.home.HomeFragment
import com.example.xde.ui.home.Task
import com.example.xde.ui.home.TaskAdapter

class InProgressFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val inProgressTaskList = mutableListOf<Task>()
    private val fragmentManager = requireActivity().supportFragmentManager
    private val homeFragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName) as HomeFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_in_progress, container, false)
        
        recyclerView = root.findViewById(R.id.inProgressRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(inProgressTaskList)
        taskAdapter.notifyDataSetChanged()
        recyclerView.adapter = taskAdapter



        return root
    }

}

