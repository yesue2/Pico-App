package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pico.R
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun AddTodoForm(viewModel: DailyTodoViewModel) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dueDate = remember { mutableStateOf("") }
    val importance = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }

    val icon = painterResource(id = R.drawable.ic_shopping)

    CardBox(txt = "어떤 일을 해볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                options = listOf("업무", "개인", "쇼핑", "공부", "가족 및 친구", "금융", "건강", "취미", "기타"),
                onValueChange = { category.value = it }
            )

            InputField(
                label = "날짜",
                placeholder = "언제까지 끝낼까요?",
                value = dueDate.value,
                onValueChange = { dueDate.value = it }
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
                    // 사용자가 입력한 값을 ViewModel로 전달
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
                            "기타" -> 9
                            else -> 0
                        },
                        dueDate = dueDate.value.toLongOrNull(),
                        importance = when (importance.value) {
                            "낮음" -> 1
                            "보통" -> 2
                            "높음" -> 3
                            else -> 0
                        }
                    )
                    viewModel.insertDaily(todo)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    showError: Boolean = false,
    errorMessage: String = "필수 입력 항목입니다."
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 17.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 15.sp,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFDFEAF2),
                unfocusedBorderColor = Color(0xFFDFEAF2),
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            isError = showError
        )

        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
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
