package com.yesue2.pico.ui.views

import androidx.compose.foundation.background
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
import com.yesue2.pico.ui.components.TaskTodoList
import com.yesue2.pico.ui.components.TopAppBar
import com.yesue2.pico.viewmodel.DailyTodoViewModel

@Composable
fun ScheduleScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(screen = "Schedule") },
        bottomBar = { BottomAppBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                DailyTodoListSection(navController)
            }
        }
    }
}

@Composable
fun DailyTodoListSection(navController: NavController, viewModel: DailyTodoViewModel = hiltViewModel()) {
    val allDailyTodos by viewModel.allDailyTodos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "할 일 목록",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskTodoList("어떤 일을 차근차근 해볼까요?", allDailyTodos, navController)
    }
}
