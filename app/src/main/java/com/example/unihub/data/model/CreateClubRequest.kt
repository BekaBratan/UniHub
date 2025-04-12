package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class CreateClubRequest(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double
)