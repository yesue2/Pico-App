package com.example.pico.ui.components

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun AddTodoForm(navController: NavController, viewModel: DailyTodoViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val title = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }
    val dueDate = rememberSaveable { mutableStateOf<Long?>(null) }
    val importance = rememberSaveable { mutableStateOf("") }
    val category = rememberSaveable { mutableStateOf("") }

    CardBox(txt = "어떤 일을 해볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            InputField(
                label = "제목",
                placeholder = "무슨 일을 할까요?",
                value = title.value,
                onValueChange = { title.value = it }
            )

            DropdownField(
                label = "종류",
                placeholder = "카테고리를 골라보세요!",
                value = category.value,
                options = listOf("업무", "개인", "쇼핑", "공부", "가족 및 친구", "금융", "건강", "취미", "집안일", "기타"),
                onValueChange = { category.value = it }
            )

            DatePickerField(
                label = "날짜",
                context = "언제까지 끝낼까요?",
                selectedDate = dueDate.value,
                onDateSelected = { date ->
                    dueDate.value = date
                }
            )

            InputField(
                label = "메모",
                placeholder = "기억하고 싶은 말 남기기",
                value = description.value,
                onValueChange = { description.value = it }
            )

            DropdownField(
                label = "중요도",
                placeholder = "얼마나 중요한가요?",
                value = importance.value,
                options = listOf("낮음", "보통", "높음"),
                onValueChange = { importance.value = it }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                SummitButton(content = "Do it!") {
                    // 사용자가 입력한 값 ViewModel로 전달
                    val todo = DailyTodoEntity(
                        title = title.value,
                        description = description.value,
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
                        dueDate = dueDate.value,
                        importance = when (importance.value) {
                            "낮음" -> 1
                            "보통" -> 2
                            "높음" -> 3
                            else -> 0
                        }
                    )
                    viewModel.insertDaily(todo)

                    Toast.makeText(context, "할 일이 저장되었어요!\uD83C\uDF89 \n 멋진 하루를 만들어봐요 \uD83D\uDCAA", Toast.LENGTH_SHORT).show()

                    navController.navigate("schedule") {
                        popUpTo("add") { inclusive = true }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun TodoPreview() {
    PicoTheme {
//        AddTodoForm(viewModel = DailyTodoViewModel())
    }
}
