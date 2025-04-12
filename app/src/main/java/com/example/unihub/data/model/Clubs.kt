package com.example.unihub.data.model

import com.google.gson.annotations.SerializedName

data class Clubs(
    @SerializedName("id")
    val id: String = "67f2f4ebb2608eae089764bf",
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
