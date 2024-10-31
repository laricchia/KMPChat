package com.github.laricchia.kmpchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.laricchia.kmpchat.chat.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val chatClient: ChatClient = ChatClient(),
) : ViewModel() {

    data class UiState(
        val messages: List<Pair<Message, Boolean>>,
    )

    private val messagesFlow: MutableStateFlow<List<Pair<Message, Boolean>>> =
        MutableStateFlow(listOf())

    val state = messagesFlow
        .map { UiState(messages = it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(messages = emptyList()))

    init {
        chatClient
            .messageFlow
            .onEach { message ->
                messagesFlow.update {
                    it.toMutableList()
                        .apply { add(message to (message.username == chatClient.username)) }
                        .toList()
                }
            }
            .launchIn(viewModelScope)
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            chatClient.sendMessage(text)
        }
    }

}
