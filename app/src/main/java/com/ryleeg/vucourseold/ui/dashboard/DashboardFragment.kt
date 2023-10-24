package com.ryleeg.vucourseold.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ryleeg.vucourseold.R
import com.ryleeg.vucourseold.databinding.FragmentDashboardBinding
import com.ryleeg.vucourseold.ui.course.CourseListAdapter
import com.ryleeg.vucourseold.ui.schedule.ScheduleAdapter
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private val viewModel: DashboardViewModel by activityViewModels()

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.welcomeText
        viewModel.getUser()


        val recyclerViewCourse = root.findViewById<RecyclerView>(R.id.recycler_view_courses)
        val recyclerViewSchedule = root.findViewById<RecyclerView>(R.id.recycler_view_schedule)

        recyclerViewCourse.layoutManager = LinearLayoutManager(this.context)
        recyclerViewSchedule.layoutManager = LinearLayoutManager(this.context)

        // Initialize the adapter with an empty list
        val adapterCourse = CourseListAdapter(emptyList())
        val adapterSchedule = ScheduleAdapter(emptyList())

        recyclerViewCourse.adapter = adapterCourse
        recyclerViewSchedule.adapter = adapterSchedule

        viewModel.viewModelScope.launch {
            val courses = viewModel.getCourses()
            val schedule = viewModel.getSchedule()

            adapterCourse.addData(courses)
            adapterSchedule.addData(schedule)
        }
        viewModel.welcomeText.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}