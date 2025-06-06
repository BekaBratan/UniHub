package com.example.unihub.data.model.auth


import com.google.gson.annotations.SerializedName

data class VerifyEmailResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("user")
    val user: User?
)