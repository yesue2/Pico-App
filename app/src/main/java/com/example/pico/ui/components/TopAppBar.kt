package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.theme.PicoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(screen: String/*, navController: NavController*/) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = screen,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun TopPreview() {
    val navController = rememberNavController()
    PicoTheme {
        TopAppBar("Home")
    }
}