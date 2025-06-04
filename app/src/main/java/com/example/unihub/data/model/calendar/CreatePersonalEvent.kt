package com.example.unihub.data.model.calendar


import com.google.gson.annotations.SerializedName

data class CreatePersonalEvent(
    @SerializedName("date")
    val date: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("eventName")
    val eventName: String?,
    @SerializedName("remindMe")
    val remindMe: Boolean?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("suggestions")
    val suggestions: String?
)