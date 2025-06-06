package com.example.unihub.data.model.auth


import com.google.gson.annotations.SerializedName

data class SignUpErrorMessage(
    @SerializedName("error")
    val error: String?,
    @SerializedName("message")
    val message: String?
)