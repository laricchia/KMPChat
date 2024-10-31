package com.github.laricchia.kmpchat.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.laricchia.kmpchat.chat.Message

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    messages: List<Pair<Message, Boolean>>,
) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = messages.size) { index ->
            val (message, isMine) = messages[index]
            MessageBox(message, isMine)
        }
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty() && state.layoutInfo.visibleItemsInfo.last().index == messages.size - 2) {
            state.animateScrollToItem(messages.size - 1)
        }
    }

}
