package com.example.unihub.data.model.admin


import com.google.gson.annotations.SerializedName

data class UpdateRoleRequest(
    @SerializedName("role")
    val role: String?
)