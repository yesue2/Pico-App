package com.example.pico.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.data.monthly.MonthlyGoalEntity
import com.example.pico.viewmodel.MonthlyGoalViewModel

@Composable
fun AddMonthlyGoalForm(navController: NavController, viewModel: MonthlyGoalViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val title = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val importance = remember { mutableStateOf("") }
    val goalAmount = remember { mutableStateOf("") } // 목표 수량
    val unit = remember { mutableStateOf("") } // 목표 단위
    val trackingMethod = remember { mutableStateOf("") } // 진행 방식
    val progress = remember { mutableStateOf(0) } // 진행도
    val startDate = remember { mutableStateOf<Long?>(null) }
    val endDate = remember { mutableStateOf<Long?>(null) }


    CardBox(txt = "이번 달엔 어떤 목표를 이루어 볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 제목 입력
            InputField(
                label = "제목",
                placeholder = "어떤 목표인가요?",
                value = title.value,
                onValueChange = { title.value = it }
            )

            // 카테고리 선택
            DropdownField(
                label = "종류",
                placeholder = "카테고리를 골라보세요!",
                value = category.value,
                options = listOf("업무", "개인", "쇼핑", "공부", "가족 및 친구", "금융", "건강", "취미", "집안일", "기타"),
                onValueChange = { category.value = it }
            )

            // 중요도 선택
            DropdownField(
                label = "중요도",
                placeholder = "얼마나 중요한가요?",
                value = importance.value,
                options = listOf("낮음", "보통", "높음"),
                onValueChange = { importance.value = it }
            )

            // 목표 수량 입력
            InputField(
                label = "목표 수량",
                placeholder = "달성 목표량을 정해보세요!",
                value = goalAmount.value,
                onValueChange = { goalAmount.value = it }
            )

            // 단위 선택
            DropdownField(
                label = "단위",
                placeholder = "목표량의 단위를 선택하세요!",
                value = unit.value,
                options = listOf("개", "권", "원", "시간", "일", "회", "KM", "기타"),
                onValueChange = { unit.value = it }
            )

            // 기간 설정
            DatePickerField(
                label = "시작 날짜",
                selectedDate = startDate.value,
                onDateSelected = { startDate.value = it }
            )
            DatePickerField(
                label = "종료 날짜",
                selectedDate = endDate.value,
                onDateSelected = { endDate.value = it }
            )

            // 진행 방식 선택
            DropdownField(
                label = "진행 방식",
                placeholder = "목표를 어떻게 기록할까요?",
                value = trackingMethod.value,
                options = listOf("직접 입력", "하루 1 증가", "수동 체크", "자동 계산"),
                onValueChange = { trackingMethod.value = it }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                SummitButton(content = "Goal in!") {
                    // 사용자가 입력한 값 ViewModel로 전달
                    val goal = MonthlyGoalEntity(
                        title = title.value,
                        isCompleted = false,
                        category = when (category.value) {
                            "업무" -> 1
                            "개인" -> 2
                            "쇼핑" -> 3
                            "공부" -> 4
                            "가족 및 친구" -> 5
                            "금융" -> 6
                            "건강" -> 7
                            "취미" -> 8
                            "집안일" -> 9
                            "기타" -> 10
                            else -> 0
                        },
                        importance = when (importance.value) {
                            "낮음" -> 1
                            "보통" -> 2
                            "높음" -> 3
                            else -> 0
                        },
                        goalAmount = goalAmount.value.toIntOrNull() ?: 0,
                        unit = unit.value,
                        startDate = startDate.value ?: System.currentTimeMillis(),
                        endDate = endDate.value ?: System.currentTimeMillis(),
                        progress = progress.value,
                        trackingMethod = trackingMethod.value
                    )
                    viewModel.insertGoal(goal)

                    Toast.makeText(context, "목표가 저장되었어요! 🎯\n성공적인 한 달을 만들어봐요 💪", Toast.LENGTH_SHORT).show()

                    navController.navigate("home") {
                        popUpTo("add") { inclusive = true }
                    }
                }
            }
        }
    }
}
