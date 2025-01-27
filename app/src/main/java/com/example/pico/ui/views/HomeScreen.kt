package com.example.pico.ui.views

import android.content.res.Configuration
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pico.R
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.GoalCard
import com.example.pico.ui.components.TaskCard
import com.example.pico.ui.components.TopAppBar
import com.example.pico.ui.theme.BackBlue
import com.example.pico.ui.theme.BackGreen
import com.example.pico.ui.theme.BackYellow
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: DailyTodoViewModel) {

    // Home 들어오면 room database 자동 삭제
/*    LaunchedEffect(Unit) {
        viewModel.deleteAllTodos()
    }*/

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
                TodayTasksSection()
            }

            item {
                AchievementChartSection()
            }
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
fun TodayTasksSection() {
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

        val tasks = listOf(
            "수빈, 연서 만나기" to "13시 판교역 2번출구",
            "영은이 생일선물 사기" to "핸드크림",
            "재활용 쓰레기 버리기" to "23시까지",
            "토익 단어 외우기" to "Day.29",
            "헬스장 가기" to "19 ~ 20시"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tasks.forEach { (title, detail) ->
                TaskCard(
                    title = title,
                    detail = detail,
                    painterResource(id = R.drawable.ic_friend),
                    BackBlue
                )
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
