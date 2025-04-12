package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class CreateEventRequest(
    @SerializedName("club")
    val club: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)