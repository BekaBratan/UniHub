package com.example.unihub.data.model

import com.google.gson.annotations.SerializedName

data class Posts(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("club")
    val club: Clubs = Clubs(),
    @SerializedName("title")
    val title: String = "HI THERE! We are PUZZLE club!",
    @SerializedName("postedAt")
    val postedAt: String = "49m",
    @SerializedName("description")
    val description: String = "HI THERE! We are PUZZLE club!",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("isLiked")
    val isLiked: Boolean = false,
)
