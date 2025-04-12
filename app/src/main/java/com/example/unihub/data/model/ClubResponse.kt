package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class ClubResponse(
    @SerializedName("club")
    val club: Club
)