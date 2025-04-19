package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class EventsResponseItem(
    @SerializedName("club")
    val club: Any? = "No Club",
    @SerializedName("date")
    val date: String = "No date",
    @SerializedName("description")
    val description: String = "No description",
    @SerializedName("_id")
    val id: String = "No id",
    @SerializedName("location")
    val location: String = "No location",
    @SerializedName("name")
    val name: String = "No name",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("__v")
    val v: Int = 0
)