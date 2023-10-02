package com.example.customview.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import com.example.customview.data.model.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(
    bars: List<Bar>
) {
    var terminalState by rememberTerminalState(barList = bars)

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (terminalState.visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(
                minimumValue = MIN_VISIBLE_BARS_COUNT,
                maximumValue = bars.size
            )
        val scrolledBy = (terminalState.scrolledBy + panChange.x)
            .coerceAtLeast(minimumValue = 0f)
            .coerceAtMost(maximumValue = bars.size * terminalState.barWidth - terminalState.terminalWidth)

        terminalState = terminalState.copy(
            visibleBarsCount = visibleBarsCount,
            scrolledBy = scrolledBy
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .transformable(state = transformableState)
            .onSizeChanged {
                terminalState = terminalState.copy(
                    terminalWidth = it.width.toFloat()
                )
            },
        onDraw = {
            val maxHeightPoint = terminalState.visibleBars.maxOf { it.high }
            val minHeightPoint = terminalState.visibleBars.minOf { it.high }
            val pxPerPoint = size.height / (maxHeightPoint - minHeightPoint)

            translate(left = terminalState.scrolledBy) {
                bars.forEachIndexed { index, bar ->
                    val offsetX =
                        size.width - index * terminalState.barWidth //чтоб свечи рисовали справа налево
                    drawLine(
                        color = Color.White,
                        start = Offset(
                            x = offsetX,
                            y = size.height - (bar.low - minHeightPoint) * pxPerPoint
                        ),
                        end = Offset(
                            x = offsetX,
                            y = size.height - (bar.high - minHeightPoint) * pxPerPoint
                        ),
                        strokeWidth = 1f
                    )

                    drawLine(
                        color = if (bar.open < bar.close) Color.Green else Color.Red,
                        start = Offset(
                            x = offsetX,
                            y = size.height - (bar.low - minHeightPoint) * pxPerPoint
                        ),
                        end = Offset(
                            x = offsetX,
                            y = size.height - (bar.high - minHeightPoint) * pxPerPoint
                        ),
                        strokeWidth = terminalState.barWidth / 2
                    )
                }
            }
        }
    )
}