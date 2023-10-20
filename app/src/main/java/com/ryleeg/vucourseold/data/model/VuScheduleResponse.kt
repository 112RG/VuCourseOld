package com.ryleeg.vucourseold.data.model

data class VuScheduleResponse(
    val schedule: List<ScheduleItem>
)

data class ScheduleItem(
    val name: String,
    val description: String
)
