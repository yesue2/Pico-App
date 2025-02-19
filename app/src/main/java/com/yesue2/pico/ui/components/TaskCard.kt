package com.yesue2.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yesue2.pico.R
import com.yesue2.pico.ui.theme.BackBlue
import com.yesue2.pico.ui.theme.PicoTheme
import com.yesue2.pico.ui.theme.Yellow90

@Composable
fun TaskCard(
    id: Int,
    title: String,
    detail: String,
    icon: Painter,
    backgroundColor: Color,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBox(title = title, detail = detail, icon = icon, backgroundColor = backgroundColor)
            Text(
                text = "자세히 보기",
                fontSize = 13.sp,
                color = Yellow90,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("detailTodo/$id")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun TaskPreview() {
    val navController = rememberNavController()
    PicoTheme {
        TaskCard(
            id = 1,
            title = "수빈, 연서 만나기",
            detail = "13시 판교역 2번 출구",
            icon = painterResource(id = R.drawable.ic_friend),
            backgroundColor = BackBlue,
            navController = navController
        )
    }
}