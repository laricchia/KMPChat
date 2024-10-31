package com.github.laricchia.kmpchat.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.laricchia.kmpchat.chat.Message
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageBox(
    message: Message,
    isMine: Boolean,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val maxSize = remember { derivedStateOf { maxWidth.times(.75f) } }
        Column(
            modifier = Modifier
                .widthIn(max = maxSize.value)
                .width(IntrinsicSize.Max)
                .clip(RoundedCornerShape(8.dp))
                .background(color = (if (isMine) Color.Green else Color.Red).copy(alpha = .5f))
                .padding(8.dp)
                .align(if (isMine) Alignment.TopEnd else Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (isMine) Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier,
                    text = if (isMine) "Me" else message.username,
                    fontSize = 12.sp,
                )
            }

            Text(
                text = message.content,
                fontSize = 16.sp,
            )
        }
    }

}

@Preview
@Composable
fun MessagePreview() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MessageBox(
            message = Message("Other", "Content!"),
            isMine = false,
        )
        MessageBox(
            Message("Me", "Content :)"),
            isMine = true,
        )
        MessageBox(
            Message("Me", "Content very very very very very very very long :)"),
            isMine = true,
        )
        MessageBox(
            Message("Me", "Content very very very very very very very long but very that it must go on two lines lol :)"),
            isMine = true,
        )
        MessageBox(
            message = Message("Other", "Content! Plz add some content!"),
            isMine = false,
        )
    }
}
