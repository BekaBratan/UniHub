package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class SimpleMessageResponse(
    @SerializedName("message")
    val message: String
)