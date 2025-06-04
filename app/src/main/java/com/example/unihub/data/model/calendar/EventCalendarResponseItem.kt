package com.example.unihub.data.model.calendar


import com.google.gson.annotations.SerializedName

data class EventCalendarResponseItem(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("persons")
    val persons: Int?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)