package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class ClubsResponse(
    @SerializedName("clubs")
    val clubs: List<Club>
)