package com.ryleeg.vucourseold.ui.course

import androidx.lifecycle.ViewModel
import com.ryleeg.vucourseold.PreferencesManager
import com.ryleeg.vucourseold.data.VuApi
import com.ryleeg.vucourseold.data.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val vuApi: VuApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    suspend fun getCourses(): List<Course> {
        return vuApi.getCourses().courses
    }
}