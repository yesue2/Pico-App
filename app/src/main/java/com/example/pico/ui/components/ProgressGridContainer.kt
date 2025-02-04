package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pico.ui.theme.PicoTheme
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun ProgressGridContainer(progress: Int, goalAmount: Int) {
    val boxSize = 20.dp // 네모 크기
    val spacing = 4.dp  // 간격

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .padding(vertical = 12.dp, horizontal = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        ProgressGrid(progress, goalAmount, boxSize, spacing)
    }
}

@Composable
fun ProgressGrid(
    progress: Int,
    goalAmount: Int,
    boxSize: androidx.compose.ui.unit.Dp,
    spacing: androidx.compose.ui.unit.Dp
) {
    Layout(
        content = {
            repeat(goalAmount) { index ->
                Box(
                    modifier = Modifier
                        .size(boxSize)
                        .background(
                            if (index < progress) MaterialTheme.colorScheme.primary // 진한 노란색
                            else Color(0xFFFFE582), // 연한 노란색
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    ) { measurables, constraints ->
        val availableWidth = constraints.maxWidth.toFloat()
        val columnWidth = boxSize.toPx() + spacing.toPx() // 각 네모의 실제 너비
        val maxColumns = (availableWidth / columnWidth).toInt() // 한 줄에 들어갈 최대 개수
        val columns = max(1, maxColumns) // 최소한 1개는 들어가도록
        val rows = ceil(goalAmount.toDouble() / columns).toInt() // 전체 행 개수

        val placeables = measurables.map { it.measure(constraints) }

        // 가로 정렬을 위한 중앙 정렬 오프셋 계산
        val totalRowWidth = (columns * columnWidth) - spacing.toPx() // 가로 총 길이
        val xOffset = (availableWidth - totalRowWidth) / 2 // 가로 중앙 정렬 오프셋

        layout(constraints.maxWidth, ((rows * (boxSize.toPx() + spacing.toPx())).toInt())) {
            var xPosition = xOffset.toInt()
            var yPosition = 0
            placeables.forEachIndexed { index, placeable ->
                if (index % columns == 0 && index != 0) {
                    xPosition = xOffset.toInt()
                    yPosition += (boxSize.toPx() + spacing.toPx()).toInt()
                }
                placeable.placeRelative(xPosition, yPosition)
                xPosition += (boxSize.toPx() + spacing.toPx()).toInt()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun ProgressPreview() {
    PicoTheme {
        ProgressGridContainer(39, 59)
    }
}
