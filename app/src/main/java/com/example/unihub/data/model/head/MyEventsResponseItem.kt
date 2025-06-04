package com.example.unihub.data.model.head


import com.google.gson.annotations.SerializedName

data class MyEventsResponseItem(
    @SerializedName("clubId")
    val clubId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("eventDate")
    val eventDate: String? = null,
    @SerializedName("eventName")
    val eventName: String? = null,
    @SerializedName("goal")
    val goal: String? = null,
    @SerializedName("headId")
    val headId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("organizers")
    val organizers: String? = null,
    @SerializedName("schedule")
    val schedule: String? = null,
    @SerializedName("shortDescription")
    val shortDescription: String? = null,
    @SerializedName("sponsorship")
    val sponsorship: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
)