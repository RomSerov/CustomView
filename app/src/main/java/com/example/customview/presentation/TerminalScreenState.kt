package com.example.customview.presentation

import com.example.customview.data.model.Bar

sealed class TerminalScreenState {
    object Initial: TerminalScreenState()
    data class Content(val barList: List<Bar>): TerminalScreenState()
}
