package com.example.pico.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pico.TodoApp
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.CardBox
import com.example.pico.ui.components.SummitButton
import com.example.pico.ui.components.TopAppBar
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel
import com.example.pico.viewmodel.DailyTodoViewModelFactory

@Composable
fun DetailScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(screen = "Detail") },
        bottomBar = { BottomAppBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
        ) {
            item {
                DetailTodoListSection()
            }
        }
    }
}

@Composable
fun DetailTodoListSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "할 일 자세히 보기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailTodoForm()
    }
}

@Composable
fun DetailTodoForm() {
    CardBox(txt = "해야할 일, 같이 확인해볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            // 제목과 마감일
            RowWithTitleAndDate()

            Spacer(modifier = Modifier.height(30.dp))

            // 메모와 세부 내용
            DetailTodoMemo()

            Spacer(modifier = Modifier.height(40.dp))

            // 완료 여부와 버튼
            CompletionSection()
        }
    }
}

@Composable
fun RowWithTitleAndDate() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "영은이 생일선물 사기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "2025년 2월 1일까지",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
            )
        }

        Text(
            text = "D-7",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DetailTodoMemo() {
    Column {
        Text(
            text = "핸드크림 \n\n" +
                    "영은이가 좋아하는 우드향!\n" +
                    "⇒ 이솝에서 우드향 나는 핸드크림이 있을까?\n\n" +
                    "1. 연서랑 같이 판교 현대백화점에서 한 번만 말아보기\n" +
                    "2. 온라인몰과 가격 비교",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)
        )
    }
}

@Composable
fun CompletionSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "할 일을 끝냈나요?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Switch(
                checked = true, // 상태 변경
                onCheckedChange = { /* 완료 상태 변경 처리 */ },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White, // 활성 상태 버튼(원) 색상
                    uncheckedThumbColor = MaterialTheme.colorScheme.tertiary, // 비활성 상태 버튼(원) 색상
                    checkedTrackColor = Color(0xFFFFD54F), // 활성 상태 트랙 색상 (노란색)
                    uncheckedTrackColor = Color(0xFFDFEAF2), // 비활성 상태 트랙 색상 (연파란색)
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                )
            )
            Text(
                text = "네, 깔끔하게 완료했어요! \uD83C\uDF89",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 30.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            SummitButton(content = "Back to list") {

            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun DetailPreview() {
    val navController = rememberNavController()

    PicoTheme {
        DetailScreen(navController)
    }
}
