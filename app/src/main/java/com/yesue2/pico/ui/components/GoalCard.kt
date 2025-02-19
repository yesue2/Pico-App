package com.yesue2.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.yesue2.pico.R
import com.yesue2.pico.ui.theme.BackGreen
import com.yesue2.pico.ui.theme.PicoTheme

@Composable
fun GoalCard(goal: String, progress: String, icon: Painter, backgroundColor: Color) {
    val displayedGoal = if (goal.length > 10) goal.take(8) + "..." else goal

    Surface(
        modifier = Modifier
            .height(80.dp)
            .width(210.dp),
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // 아이콘
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 텍스트
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = displayedGoal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = progress,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun GoalPreview() {
    PicoTheme {
        GoalCard(
            goal = "책 3권 이상 읽기",
            progress = "2 / 3",
            icon = painterResource(id = R.drawable.ic_study), // 드로어블 리소스 설정
            backgroundColor = BackGreen // 배경 색상
        )
    }
}
