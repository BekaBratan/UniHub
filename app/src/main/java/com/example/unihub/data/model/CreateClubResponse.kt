package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class CreateClubResponse(
    @SerializedName("club")
    val club: Club,
    @SerializedName("message")
    val message: String
)