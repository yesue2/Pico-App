package com.example.pico.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pico.R
import com.example.pico.ui.theme.MainYellow

@Composable
fun NextButton(onClick: () -> Unit) {
    val openingIconPainter: Painter = painterResource(id = R.drawable.ic_next)

    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Next",
            fontSize = 24.sp,
            color = MainYellow
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = openingIconPainter,
            contentDescription = "Next Icon",
            tint = MainYellow,
            modifier = Modifier.size(25.dp)
        )
    }
}