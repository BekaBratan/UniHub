package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("confirmPassword")
    val confirmPassword: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("surname")
    val surname: String
)