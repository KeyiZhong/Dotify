package com.kz25.dotify.manager.model

data class UserInfo(
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Double,
    val profilePicURL: String
)