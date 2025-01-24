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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.components.AddTodoForm
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.TopAppBar
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun AddScreen(navController: NavController, viewModel: DailyTodoViewModel) {
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
                AddTodoListSection(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AddTodoListSection(viewModel: DailyTodoViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "할 일 추가하기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddTodoForm(viewModel = viewModel)
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
