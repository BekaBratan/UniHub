package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class VerificationResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)