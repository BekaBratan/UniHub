package com.example.unihub.data.model.calendar


import com.google.gson.annotations.SerializedName

data class PersonalCalendarResponseItem(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("eventName")
    val eventName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("remindMe")
    val remindMe: Boolean?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("suggestions")
    val suggestions: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)