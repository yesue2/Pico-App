package com.yesue2.pico.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yesue2.pico.ui.components.BottomAppBar
import com.yesue2.pico.ui.components.TaskGoalList
import com.yesue2.pico.ui.components.TaskTodoList
import com.yesue2.pico.ui.components.TopAppBar
import com.yesue2.pico.viewmodel.DailyTodoViewModel
import com.yesue2.pico.viewmodel.MonthlyGoalViewModel

@Composable
fun MyScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(screen = "My") },
        bottomBar = { BottomAppBar(navController = navController) }
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
                CompletedDailyTodoListSection(navController)
            }

            item {
                CompletedGoalListSection(navController)
            }
        }
    }
}

@Composable
fun CompletedDailyTodoListSection(navController: NavController, viewModel: DailyTodoViewModel = hiltViewModel()) {
    val completedDailyTodos by viewModel.completedDailyTodos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "지난 할 일 목록",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskTodoList("매일 멋지게 해냈어요! \uD83D\uDC4F", completedDailyTodos, navController)
    }
}

@Composable
fun CompletedGoalListSection(navController: NavController, viewModel: MonthlyGoalViewModel = hiltViewModel()) {
    val completedGoals by viewModel.completedMonthlyGoals.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "지난 목표 목록",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskGoalList("매달 끈기가 대단해요! 👍", completedGoals, navController)
    }
}