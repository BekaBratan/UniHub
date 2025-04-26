package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class VerificationRequest(
    @SerializedName("code")
    val code: String,
    @SerializedName("email")
    val email: String
)