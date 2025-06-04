package com.example.unihub.data.model.calendar


import com.google.gson.annotations.SerializedName

data class CombinedCalendarResponse(
    @SerializedName("events")
    val events: List<Event?>?,
    @SerializedName("personalEvents")
    val personalEvents: Int?,
    @SerializedName("ticketEvents")
    val ticketEvents: Int?,
    @SerializedName("totalEvents")
    val totalEvents: Int?
)