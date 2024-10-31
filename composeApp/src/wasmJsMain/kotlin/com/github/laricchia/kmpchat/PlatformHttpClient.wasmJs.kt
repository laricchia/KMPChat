package com.github.laricchia.kmpchat

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.websocket.*

actual object PlatformHttpClient {
    actual val client: HttpClient
        get() = HttpClient(Js) {
            configureWithWebsockets()
        }
    actual val host: String = "127.0.0.1"
    actual val baseUsername = "Wasm"
}
