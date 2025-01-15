package com.example.pico.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pico.R
import com.example.pico.ui.theme.MainYellow
import com.example.pico.ui.theme.PicoTheme

@Composable
fun StartScreen3() {
    val backgroundPainter: Painter = painterResource(id = R.drawable.background_start)
    val openingIconPainter: Painter = painterResource(id = R.drawable.ic_openning1)
    val summitButtonPainter: Painter = painterResource(id = R.drawable.btn_summit)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = openingIconPainter,
            contentDescription = "Opening Icon",
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center)
                .offset(y = (-100).dp)
        )

        // Text Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 30.dp)
                .padding(bottom = 180.dp), // Adjust spacing for button
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pico는",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "여러분의 바쁜 하루를",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "더 가볍고 행복하게 만들어줄 거예요.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "지금 바로 Pico와 함께 시작해볼까요?",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = summitButtonPainter,
                contentDescription = "Summit Button Background",
                modifier = Modifier
                    .size(width = 350.dp, height = 65.dp)
            )

            Text(
                text = "Let’s Start",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
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
fun Start3Preview() {
    PicoTheme {
        StartScreen3()
    }
}
