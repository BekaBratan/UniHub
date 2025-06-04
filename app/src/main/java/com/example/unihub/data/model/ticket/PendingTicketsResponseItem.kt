package com.example.unihub.data.model.ticket


import com.google.gson.annotations.SerializedName

data class PendingTicketsResponseItem(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("numberOfPersons")
    val numberOfPersons: Int?,
    @SerializedName("paymentProof")
    val paymentProof: String?,
    @SerializedName("poster")
    val poster: PosterX?,
    @SerializedName("user")
    val user: User?
)