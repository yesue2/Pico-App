package com.example.pico.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pico.R
import com.example.pico.ui.theme.PicoTheme

@Composable
fun SummitButton(content: String, onClick: () -> Unit) {
    val summitButtonPainter: Painter = painterResource(id = R.drawable.btn_summit)

    Box(
        modifier = Modifier
            .clickable ( onClick = onClick ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = summitButtonPainter,
            contentDescription = "Summit Button Background",
            modifier = Modifier
                .size(width = 350.dp, height = 65.dp)
        )

        Text(
            text = content,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SummitPreview() {
    PicoTheme {
        SummitButton("Let's Start") {

        }
    }
}
