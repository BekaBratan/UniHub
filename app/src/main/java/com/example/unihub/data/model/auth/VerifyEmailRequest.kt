package com.example.unihub.data.model.auth


import com.google.gson.annotations.SerializedName

data class VerifyEmailRequest(
    @SerializedName("code")
    val code: String?,
    @SerializedName("email")
    val email: String?
)