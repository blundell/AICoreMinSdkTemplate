package com.blundell.tut.gemini

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun SecondScreen(
    state: State,
    onSubmitRequest: (String) -> Unit,
) {
    when (state) {
        State.Idle -> {
            InputScreen(
                currentValue = "Hello Second World",
                onSubmitRequest = onSubmitRequest
            )
        }
        State.Loading -> {
            LoadingScreen()
        }
        is State.Success -> {
            InputScreen(
                currentValue = state.answer,
                onSubmitRequest = onSubmitRequest
            )
        }
    }
}

@Composable
private fun InputScreen(
    currentValue: String,
    onSubmitRequest: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(currentValue)
        Spacer(
            modifier = Modifier
                .padding(16.dp)
        )
        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Enter text") } // Optional label
        )
        Button(onClick = { onSubmitRequest(text) }) {
            Text("Submit")
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewSecondScreen() {
    SecondScreen(
        state = State.Success(answer = "Demo Success!"),
        onSubmitRequest = {}
    )
}
