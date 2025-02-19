package com.example.pico.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.components.AddMonthlyGoalForm
import com.example.pico.ui.components.AddTodoForm
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.ToggleButtonGroup
import com.example.pico.ui.components.TopAppBar
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel
import com.example.pico.viewmodel.MonthlyGoalViewModel

@Composable
fun AddScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = { TopAppBar(screen = "Add") },
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
                AddTodoListSection(navController)
            }
        }
    }
}

@Composable
fun AddTodoListSection(
    navController: NavController
) {
    var selectedTab by rememberSaveable { mutableStateOf("할 일") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "추가하기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        ToggleButtonGroup(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            "할 일" -> AddTodoForm(navController)
            "이달의 목표" -> AddMonthlyGoalForm(navController)
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun AddPreview() {
    val navController = rememberNavController()
    PicoTheme {
//        AddScreen(navController, viewModel = DailyTodoViewModel())
    }
}
