package com.example.pico.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pico.R
import com.example.pico.ui.components.SummitButton
import com.example.pico.ui.theme.MainDarkBrown
import com.example.pico.ui.theme.PicoTheme

@Composable
fun StartScreen3(navController: NavController) {
    val backgroundPainter: Painter = painterResource(id = R.drawable.background_start)
    val openingIconPainter: Painter = painterResource(id = R.drawable.ic_openning1)

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
                color = MainDarkBrown
            )
            Text(
                text = "여러분의 바쁜 하루를",
                style = MaterialTheme.typography.bodyMedium,
                color = MainDarkBrown
            )
            Text(
                text = "더 가볍고 행복하게 만들어줄 거예요.",
                style = MaterialTheme.typography.bodyMedium,
                color = MainDarkBrown
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "지금 바로 Pico와 함께 시작해볼까요?",
                style = MaterialTheme.typography.bodyMedium,
                color = MainDarkBrown
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            SummitButton(content = "Let's Start") {
                navController.navigate("home")
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
fun SPreview() {
    val navController = rememberNavController()

    PicoTheme {
        StartScreen3(navController)
    }
}