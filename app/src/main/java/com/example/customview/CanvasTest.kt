package com.example.customview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CanvasTest() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        onDraw = {
            drawLine(
                color = Color.White,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = size.height),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(x = size.width, y = 0f),
                end = Offset(x = 0f, y = size.height),
                strokeWidth = 1.dp.toPx()
            )
            drawCircle(
                color = Color.Yellow,
                radius = 100.dp.toPx(),
                style = Stroke(width = 2.dp.toPx())
            )
        }
    )
}

@Preview
@Composable
private fun CanvasTestPreview() {
    CanvasTest()
}