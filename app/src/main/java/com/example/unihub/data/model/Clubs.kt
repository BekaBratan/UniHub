package com.example.unihub.data.model

import com.google.gson.annotations.SerializedName

data class Clubs(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "Puzzle Club",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("posts")
    val posts: List<Posts> = listOf(),
    @SerializedName("isFollowed")
    val isFollowed: Boolean = false,
)
