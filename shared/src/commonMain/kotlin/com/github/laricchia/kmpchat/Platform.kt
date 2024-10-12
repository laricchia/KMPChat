package com.github.laricchia.kmpchat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform