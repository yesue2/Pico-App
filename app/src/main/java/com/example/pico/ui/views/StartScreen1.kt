package com.example.pico.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pico.R
import com.example.pico.ui.components.NextButton
import com.example.pico.ui.theme.MainDarkBrown
import com.example.pico.ui.theme.MainYellow
import com.example.pico.ui.theme.PicoTheme

@Composable
fun StartScreen1(navController: NavController) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(30.dp)
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = MainDarkBrown,
                text = buildAnnotatedString {
                    append("✨ ")
                    withStyle(style = SpanStyle(MainYellow)) {
                        append("Pico")
                    }
                    append("와 함께 오늘도 ")
                    withStyle(style = SpanStyle(MainYellow)) {
                        append("똑똑")
                    }
                    append("하게!")
                },
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "작고 간편한 Todo 리스트 앱",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Pico에 오신 걸 환영해요!",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MainYellow, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MainDarkBrown, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MainDarkBrown, shape = CircleShape)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                NextButton(onClick = {
                    navController.navigate("Start2")
                })
            }
        }
    }
}
