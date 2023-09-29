package com.example.customview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CanvasTapTest() {

    var points by rememberSaveable {
        mutableStateOf<List<Offset>>(listOf())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    points = points + it
                }
            },
        onDraw = {
            points.forEach {
                drawCircle(
                    brush = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan, Color.Red)),
                    radius = 10.dp.toPx(),
                    center = it
                )
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CanvasDragTest() {

    var points by rememberSaveable {
        mutableStateOf<List<PointerDrag>>(listOf())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragStart = {
                        points = points + PointerDrag(
                            offset = it,
                            isStartedPosition = true
                        )
                    },
                    onDrag = { change, _ ->
                        points = points + change.historical.map {
                            PointerDrag(
                                offset = it.position,
                                isStartedPosition = false
                            )
                        }
                    }
                )
            },
        onDraw = {
            val path = Path()
            points.forEach {
                if (it.isStartedPosition) {
                    path.moveTo(x = it.offset.x, y = it.offset.y)
                } else {
                    path.lineTo(x = it.offset.x, y = it.offset.y)
                }
            }
            drawPath(
                path = path,
                brush = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan, Color.Red)),
                style = Stroke(width = 5.dp.toPx())
            )
        }
    )
}

data class PointerDrag(
    val offset: Offset,
    val isStartedPosition: Boolean
)

@Preview
@Composable
private fun CanvasTapTestPreview() {
    CanvasTapTest()
}

@Preview
@Composable
private fun CanvasDragTestPreview() {
    CanvasDragTest()
}