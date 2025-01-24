package com.example.pico.ui.views

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.CardBox
import com.example.pico.ui.components.SummitButton
import com.example.pico.ui.components.TopAppBar
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: DailyTodoViewModel, todoId: Int) {
    LaunchedEffect(todoId) {
        viewModel.loadTodoById(todoId)
    }

    val todo = viewModel.selectedTodo.collectAsState().value


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
                todo?.let { DetailTodoListSection(it, viewModel) } ?: Text(
                    text = "뒤로가기를 눌러주세요",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun DetailTodoListSection(todo: DailyTodoEntity, viewModel: DailyTodoViewModel) {
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

        DetailTodoForm(todo, viewModel)
    }
}

@Composable
fun DetailTodoForm(todo: DailyTodoEntity, viewModel: DailyTodoViewModel) {
    CardBox(txt = "해야할 일, 같이 확인해볼까요?") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            // 제목과 마감일
            RowWithTitleAndDate(todo)

            Spacer(modifier = Modifier.height(30.dp))

            // 메모와 세부 내용
            DetailTodoMemo(todo)

            Spacer(modifier = Modifier.height(40.dp))

            // 완료 여부와 버튼
            CompletionSection(todo, viewModel)
        }
    }
}

@Composable
fun RowWithTitleAndDate(todo: DailyTodoEntity) {
    val dDay = todo.dueDate?.let { dueDate ->
        val daysRemaining = (dueDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
        if (daysRemaining > 0) "D-$daysRemaining" else "기한 지남"
    } ?: "마감일 없음"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = todo.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = todo.dueDate?.let {
                    java.text.SimpleDateFormat("yyyy년 MM월 dd일까지").format(it)
                } ?: "마감일 없음",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
            )
        }

        Text(
            text = dDay,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DetailTodoMemo(todo: DailyTodoEntity) {
    Column {
        Text(
            text = todo.description.ifEmpty { "세부 내용 없음" },
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)
        )
    }
}

@Composable
fun CompletionSection(todo: DailyTodoEntity, viewModel: DailyTodoViewModel) {
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
                checked = false, // 상태 변경
                onCheckedChange = { isCompleted ->
                    val updatedTodo = todo.copy(isCompleted = isCompleted)
                    viewModel.update(updatedTodo)
                },

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