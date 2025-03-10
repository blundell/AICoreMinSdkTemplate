package com.blundell.tut.gemini

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SecondComposable(viewModel: SecondViewModel = viewModel()) {
    val accessible = remember { viewModel.onRequestAccess() }
    if (accessible) {
        val state by viewModel.state.collectAsState()
        SecondScreen(state) {
            viewModel.onSubmit(it)
        }
    } else {
        NoAccessScreen()
    }
}

@Preview
@Composable
private fun PreviewSecondComposable() {
    SecondComposable()
}
