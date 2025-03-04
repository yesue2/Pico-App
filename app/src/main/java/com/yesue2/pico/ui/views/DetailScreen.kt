package com.yesue2.pico.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yesue2.pico.R
import com.yesue2.pico.data.daily.DailyTodoEntity
import com.yesue2.pico.ui.components.CardBox
import com.yesue2.pico.ui.components.SummitButton
import com.yesue2.pico.ui.components.TopAppBarDetail
import com.yesue2.pico.ui.theme.BackBlue
import com.yesue2.pico.ui.theme.BackGreen
import com.yesue2.pico.ui.theme.BackPink
import com.yesue2.pico.ui.theme.BackYellow
import com.yesue2.pico.viewmodel.DailyTodoViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: DailyTodoViewModel = hiltViewModel(), todoId: Int) {
    LaunchedEffect(todoId) {
        viewModel.loadDailyTodoById(todoId)
    }

    val todo = viewModel.selectedTodo.collectAsState().value


    Scaffold(
        topBar = {
            TopAppBarDetail(
                screen = "Detail",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
        ) {
            item {
                todo?.let { DetailTodoListSection(it, navController) } ?: Text(
                    text = "할 일 정보를 불러오는 데 실패했습니다. \n뒤로가기를 눌러주세요",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun DetailTodoListSection(
    todo: DailyTodoEntity,
    navController: NavController
) {
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

        DetailTodoForm(todo, navController, "해야할 일, 같이 확인해볼까요?")
    }
}

@Composable
fun DetailTodoForm(
    todo: DailyTodoEntity,
    navController: NavController,
    txt: String
) {
    CardBox(txt = txt) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            // 제목과 마감일
            RowWithTitleAndDate(todo)

            Spacer(modifier = Modifier.height(20.dp))

            // 메모와 세부 내용
            DetailTodoMemo(todo)

            Spacer(modifier = Modifier.height(20.dp))

            // 완료 여부와 버튼
            CompletionSection(todo, navController)
        }
    }
}

@Composable
fun RowWithTitleAndDate(todo: DailyTodoEntity) {
    val dDay = todo.dueDate?.let { dueDate ->
        // 현재 날짜와 마감 날짜를 일 단위로 계산
        val currentDate = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val dueDateDays = dueDate / (1000 * 60 * 60 * 24)
        val daysRemaining = (dueDateDays - currentDate).toInt()

        when {
            daysRemaining > 0 -> "D-$daysRemaining"
            daysRemaining == 0 -> "오늘 마감"
            else -> "기한 지남"
        }
    } ?: "마감일 없음"

    val (iconResId, backgroundColor) = when (todo.category) {
        1 -> R.drawable.ic_company to BackBlue
        2 -> R.drawable.ic_personal to BackPink
        3 -> R.drawable.ic_shopping to BackYellow
        4 -> R.drawable.ic_study to BackGreen
        5 -> R.drawable.ic_friend to BackBlue
        6 -> R.drawable.ic_invest to BackYellow
        7 -> R.drawable.ic_health to BackGreen
        8 -> R.drawable.ic_hobby to BackPink
        9 -> R.drawable.ic_housework to BackPink
        10 -> R.drawable.ic_etc to BackYellow
        else -> R.drawable.ic_etc to Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = backgroundColor, shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column (
                modifier = Modifier.weight(1f)
            ) {
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
        }
        Text(
            text = dDay,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 5.dp)
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
fun CompletionSection(
    todo: DailyTodoEntity,
    navController: NavController,
    viewModel: DailyTodoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isSwitchChecked by remember { mutableStateOf(todo.isCompleted) }

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
                checked = isSwitchChecked, // 상태 변경
                onCheckedChange = { isCompleted ->
                    isSwitchChecked = isCompleted
                    val updatedTodo = todo.copy(isCompleted = isCompleted)
                    viewModel.updateDaily(updatedTodo)
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
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "이 할 일 삭제하기",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        viewModel.deleteDailyTodoById(todo.id)
                        Toast.makeText(
                            context,
                            "할 일이 삭제되었어요! \n 다음에 더 나은 계획으로 도전해봐요\uD83D\uDE0C",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            SummitButton(content = "Back to list") {
                navController.popBackStack()
            }
        }
    }
}
