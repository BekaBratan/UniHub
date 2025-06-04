package com.example.unihub.data.model.ticket


import com.google.gson.annotations.SerializedName

data class PosterX(
    @SerializedName("eventDate")
    val eventDate: String?,
    @SerializedName("eventTitle")
    val eventTitle: String?,
    @SerializedName("id")
    val id: Int?
)