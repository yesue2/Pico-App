package com.yesue2.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.yesue2.pico.ui.theme.BackBlue
import com.yesue2.pico.ui.theme.PicoTheme

@Composable
fun TextBox(title: String, detail: String, icon: Painter, backgroundColor: Color) {
    val displayedTitle = if (title.length > 10) title.take(8) + "..." else title
    val displayedContext = if (detail.length > 13) detail.take(11) + "..." else title

    Surface(
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        color = backgroundColor,
                        shape = /*CircleShape*/RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center

            ) {
                Image(painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = displayedTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = displayedContext, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark"
)
@Composable
fun TextBoxPreview() {
    PicoTheme {
        TextBox(
            "수빈, 연서 만나기 ", "13시 판교역 2번 출구", painterResource(id = R.drawable.ic_friend), BackBlue
        )
    }
}