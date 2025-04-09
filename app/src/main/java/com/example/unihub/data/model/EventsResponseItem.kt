package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class EventsResponseItem(
    @SerializedName("club")
    val club: Any?,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("__v")
    val v: Int
)