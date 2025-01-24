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
import com.example.pico.ui.theme.BackYellow
import com.example.pico.ui.theme.PicoTheme

@Composable
fun TaskList(comment: String, todos: List<DailyTodoEntity>, navController: NavController) {
    CardBox(txt = comment) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            /*val tasks = listOf(
                "영은이 생일선물 사기" to "핸드크림",
                "수빈, 연서 만나기" to "13시 판교역 2번출구",
                "재활용 쓰레기 버리기" to "23시까지",
                "토익 단어 외우기" to "Day.29",
                "헬스장 가기" to "19 ~ 20시"
            )*/

            Column() {
                todos.forEach { todo ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("detail/${todo.id}")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextBox(
                            title = todo.title,
                            detail = todo.description,
                            icon = painterResource(id = R.drawable.ic_shopping),
                            backgroundColor = BackYellow
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