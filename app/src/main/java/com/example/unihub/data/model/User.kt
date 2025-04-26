package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String
)