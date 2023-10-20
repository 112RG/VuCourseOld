package com.ryleeg.vucourseold.data.model

data class VuCoursesResponse(
    val courses: List<Course>
)

data class Course(
    val name: String,
    val description: String
)
