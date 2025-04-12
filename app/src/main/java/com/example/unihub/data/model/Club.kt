package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class Club(
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("__v")
    val v: Int
)