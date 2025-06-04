package com.example.unihub.data.model.calendar


import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("endTime")
    val endTime: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("persons")
    val persons: Int? = null,
    @SerializedName("remindMe")
    val remindMe: Boolean? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("startTime")
    val startTime: String? = null,
    @SerializedName("suggestions")
    val suggestions: String? = null,
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
)