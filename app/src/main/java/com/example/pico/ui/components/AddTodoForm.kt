package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pico.ui.theme.PicoTheme

@Composable
fun AddTodoForm() {
    val title = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val memo = remember { mutableStateOf("") }

    CardBox(txt = "어떤 일을 해볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            InputField(
                label = "제목",
                placeholder = "무슨 일을 할까요?",
                value = title.value,
                onValueChange = { title.value = it }
            )

            DropdownField(label = "종류", placeholder = "카테고리를 골라보세요!")

            InputField(
                label = "날짜",
                placeholder = "언제까지 끝낼까요?",
                value = date.value,
                onValueChange = { date.value = it }
            )

            InputField(
                label = "메모",
                placeholder = "기억하고 싶은 말 남기기",
                value = memo.value,
                onValueChange = { memo.value = it }
            )

            DropdownField(label = "중요도", placeholder = "얼마나 중요한가요?")

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                SummitButton(content = "Do it!") {
                    // 제출 버튼 동작 처리
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
        AddTodoForm()
    }
}
