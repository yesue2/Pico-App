package com.example.pico.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pico.R
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.GoalCard
import com.example.pico.ui.components.TaskCard
import com.example.pico.ui.components.TopAppBar
import com.example.pico.ui.theme.BackBlue
import com.example.pico.ui.theme.BackGreen
import com.example.pico.ui.theme.BackPink
import com.example.pico.ui.theme.BackYellow
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: DailyTodoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.loadTodayTodos()
    }

    Scaffold(
        topBar = { TopAppBar(screen = "Home") },
        bottomBar = { BottomAppBar(navController = navController)}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MonthlyGoalSection()
            }

            item {
                TodayTasksSection(viewModel, navController)
            }

/*            item {
                AchievementChartSection()
            }*/
        }
    }
}

@Composable
fun MonthlyGoalSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "1월의 목표",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "이번 달엔 어떤 목표를 달성해 볼까요?",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                GoalCard(goal = "책 3권 이상 읽기", progress = "2 / 3", painterResource(id = R.drawable.ic_study), BackGreen)
            }
            item {
                GoalCard(goal = "50만원 저축", progress = "38 / 50", painterResource(id = R.drawable.ic_invest), BackYellow)
            }
        }
    }
}

@Composable
fun TodayTasksSection(viewModel: DailyTodoViewModel, navController: NavController) {
    val todayTasks = viewModel.todayTodos.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "오늘의 할 일",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "오늘도 멋진 하루를 만들어봐요!",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (todayTasks.isEmpty()) {
            Text(
                text = "오늘 완료해야 할 일이 없어요!✨ \n새로운 할 일을 추가해서 목표를 만들어보세요\uD83D\uDE0A",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                todayTasks.forEach { task ->
                    val (iconResId, backgroundColor) = when (task.category) {
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
                        else -> R.drawable.ic_etc to BackYellow
                    }

                    TaskCard(
                        id = task.id,
                        title = task.title,
                        detail = task.description,
                        icon = painterResource(id = iconResId),
                        backgroundColor = backgroundColor,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun AchievementChartSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // 적당한 여백 추가
    ) {
        Text(
            text = "이만큼 달성했어요",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "앞으로의 도전도 기대돼요!",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder for achievement chart
    }
}
