package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pico.R
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.ui.theme.BackBlue
import com.example.pico.ui.theme.BackGreen
import com.example.pico.ui.theme.BackPink
import com.example.pico.ui.theme.BackYellow
import com.example.pico.ui.theme.PicoTheme

@Composable
fun TaskList(comment: String, todos: List<DailyTodoEntity>, navController: NavController) {
    CardBox(txt = comment) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column() {
                todos.forEach { todo ->
                    val (iconRes, backgroundColor) = getCategoryIconAndColor(todo.category)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("detailTodo/${todo.id}")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextBox(
                            title = todo.title,
                            detail = todo.description,
                            icon = painterResource(id = iconRes),
                            backgroundColor = backgroundColor
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_detail),
                            contentDescription = "상세보기 버튼",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

fun getCategoryIconAndColor(category: Int): Pair<Int, androidx.compose.ui.graphics.Color> {
    return when (category) {
        1 -> Pair(R.drawable.ic_company, BackBlue)
        2 -> Pair(R.drawable.ic_personal, BackPink)
        3 -> Pair(R.drawable.ic_shopping, BackYellow)
        4 -> Pair(R.drawable.ic_study, BackGreen)
        5 -> Pair(R.drawable.ic_friend, BackBlue)
        6 -> Pair(R.drawable.ic_invest, BackYellow)
        7 -> Pair(R.drawable.ic_health, BackGreen)
        8 -> Pair(R.drawable.ic_hobby, BackPink)
        9 -> Pair(R.drawable.ic_housework, BackPink)
        10 -> Pair(R.drawable.ic_etc, BackYellow)
        else -> Pair(R.drawable.ic_etc, BackYellow) // Default
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun TaskListPreview() {
    PicoTheme {
//        TaskList("어떤 일을 차근차근 해볼까요?")
    }
}