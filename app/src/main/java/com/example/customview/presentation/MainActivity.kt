package com.example.customview.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customview.presentation.components.Terminal
import com.example.customview.ui.theme.CustomViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomViewTheme {
                val viewModel: TerminalViewModel = viewModel()
                val state = viewModel.state.collectAsState()

                when(val currentState = state.value) {
                    is TerminalScreenState.Content -> {
                        Terminal(bars = currentState.barList)
                    }
                    TerminalScreenState.Initial -> {

                    }
                }
            }
        }
    }
}