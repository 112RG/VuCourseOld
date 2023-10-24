package com.ryleeg.vucourseold.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.databinding.FragmentCourseBinding
import kotlinx.coroutines.launch

class CourseFragment : Fragment() {
    private val viewModel: CourseViewModel by activityViewModels()

    private var _binding: FragmentCourseBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourseBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this.context)

        // Initialize the adapter with an empty list
        val adapter = CourseListAdapter(emptyList())

        recyclerView.adapter = adapter
        root.findViewById<SearchView>(R.id.search_view)
            .setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        viewModel.viewModelScope.launch {
            val courses = viewModel.getCourses()
            adapter.addData(courses)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}