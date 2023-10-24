package com.ryleeg.vucourseold.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryleeg.vucourseold.data.VuApi
import com.ryleeg.vucourseold.data.model.Course
import com.ryleeg.vucourseold.data.model.ScheduleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vuApi: VuApi,
) : ViewModel() {

    val welcomeText = MutableLiveData<String>()

    fun getUser() {
        viewModelScope.launch {
            try {
                val userReq = vuApi.getUserInfo()
                // Will always be true if login passes
                welcomeText.value = "Welcome, ${userReq.userName}"
            } catch (ex: Exception) {
                welcomeText.value = "Welcome, Unknown"

            }
        }
    }

    suspend fun getCourses(): List<Course> {
        return vuApi.getCourses().courses
    }

    suspend fun getSchedule(): List<ScheduleItem> {
        return vuApi.getSchedule().schedule
    }
}