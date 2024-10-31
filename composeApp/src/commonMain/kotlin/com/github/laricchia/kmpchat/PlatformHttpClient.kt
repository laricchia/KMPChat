package com.github.laricchia.kmpchat

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json.Default

expect object PlatformHttpClient {
    val client: HttpClient
    val host: String
    val baseUsername: String
}

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureWithWebsockets() {
    install(WebSockets) {
        pingIntervalMillis = 20_000
        contentConverter = KotlinxWebsocketSerializationConverter(Default)
    }
}
