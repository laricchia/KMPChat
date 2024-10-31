package com.github.laricchia.kmpchat.chat

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val username: String,
    val content: String,
)
