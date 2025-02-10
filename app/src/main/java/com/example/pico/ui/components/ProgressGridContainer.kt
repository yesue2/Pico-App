package com.example.pico.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pico.data.monthly.MonthlyGoalEntity
import com.example.pico.viewmodel.MonthlyGoalViewModel
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun ProgressGridContainer(
    goal: MonthlyGoalEntity,
    viewModel: MonthlyGoalViewModel = hiltViewModel()
) {
    val progress = viewModel.selectedGoal.collectAsState().value?.progress ?: goal.progress
    val boxSize = 20.dp // 네모 크기
    val spacing = 4.dp  // 간격

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
            .padding(vertical = 12.dp, horizontal = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        ProgressGrid(
            progress = progress,
            goalAmount = goal.goalAmount,
            trackingMethod = goal.trackingMethod, // ✅ 진행 방식 전달
            boxSize = boxSize,
            spacing = spacing
        ) { newProgress ->
            viewModel.updateProgressManually(goal, newProgress) // 사용자가 직접 선택한 값 반영
        }
    }

    // ✅ 진행 방식이 "하루 1 증가" 또는 "자동 계산"이면 자동 업데이트 실행
    if (goal.trackingMethod in listOf("매일 1 자동 증가", "목표에 맞춰 자동 계산")) {
        LaunchedEffect(goal.trackingMethod) {
            viewModel.updateProgressAutomatically(goal)
        }
    }
}

@Composable
fun ProgressGrid(
    progress: Int,
    goalAmount: Int,
    trackingMethod: String,
    boxSize: androidx.compose.ui.unit.Dp,
    spacing: androidx.compose.ui.unit.Dp,
    onProgressChange: (Int) -> Unit // 사용자가 터치하면 진행도를 업데이트하는 함수
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
                        .clickable(enabled = trackingMethod != "진행률 기록 안 하기") { // ✅ 클릭 가능 여부 설정
                            val newProgress = when (trackingMethod) {
                                "매일 1 자동 증가", "목표에 맞춰 자동 계산" -> progress // 자동 모드에서는 사용자가 수정 불가
                                else -> index + 1 // 직접 입력 가능
                            }
                            onProgressChange(newProgress) // ViewModel로 진행도 전달
                        }

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

/*
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
*/
