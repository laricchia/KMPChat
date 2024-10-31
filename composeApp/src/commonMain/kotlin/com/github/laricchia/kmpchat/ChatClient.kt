package com.github.laricchia.kmpchat

import com.github.laricchia.kmpchat.chat.Message
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.charsets.Charsets
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.random.Random
import kotlin.random.nextInt

class ChatClient {

    private val messageChannel = Channel<Message>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val messageFlow = messageChannel.receiveAsFlow()
    private val client: HttpClient = PlatformHttpClient.client
    private val outMessageChannel = Channel<Message>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val username = "${PlatformHttpClient.baseUsername}#${Random.nextInt(0 until 1000)}"

    init {
        CoroutineScope(Dispatchers.Default).launch {
            client.webSocket(method = HttpMethod.Get, host = PlatformHttpClient.host, port = 9090, path = "/chat") {
                while (true) {
                    incoming.tryReceive().getOrNull()?.let {
                        val message = converter?.deserialize(Charsets.UTF_8, TypeInfo(Message::class), it) as Message
                        messageChannel.trySend(message)
                    }

                    outMessageChannel.tryReceive().getOrNull()?.let { message ->
                        sendSerialized(message)
                    }
                    delay(125)
                }
            }
        }
    }

    fun sendMessage(message: String) {
        outMessageChannel.trySend(Message(username, message.trim().take(1500)))
    }

}
