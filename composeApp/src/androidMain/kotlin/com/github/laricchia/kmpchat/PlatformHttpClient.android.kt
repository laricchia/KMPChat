package com.github.laricchia.kmpchat

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*

actual object PlatformHttpClient {
    actual val client: HttpClient
        get() = HttpClient(CIO) {
            configureWithWebsockets()
        }
    actual val host: String = "10.0.2.2"
    actual val baseUsername = "Android"
}
