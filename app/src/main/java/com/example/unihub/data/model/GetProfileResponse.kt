package com.example.unihub.data.model


import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followedClubs")
    val followedClubs: List<Any?>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("interests")
    val interests: List<String>,
    @SerializedName("lastActivity")
    val lastActivity: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("verified")
    val verified: Boolean
)