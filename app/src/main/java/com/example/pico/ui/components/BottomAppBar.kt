package com.example.pico.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pico.R
import com.example.pico.ui.theme.BottomItemColor
import com.example.pico.ui.theme.PicoTheme

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val selectedIcon: Int,
    val label: String
)

@Composable
fun BottomAppBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", R.drawable.ic_home, R.drawable.ic_home_selected, "Home"),
        BottomNavItem("schedule", R.drawable.ic_schedule, R.drawable.ic_schedule_selected, "Schedule"),
        BottomNavItem("add", R.drawable.ic_add, R.drawable.ic_add_selected, "Add"),
        BottomNavItem("my", R.drawable.ic_my, R.drawable.ic_my_selected, "My")
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "schedule"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            BottomNavItem(
                item = item,
                isSelected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(if (isSelected) 2f else 1f) // 선택된 항목은 더 많은 공간을 차지
            )
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = if (isSelected) Color(0x1AFFC700) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .height(48.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = if (isSelected) item.selectedIcon else item.icon),
            contentDescription = null,
            tint = if (isSelected) Color(0xFFFFC107) else BottomItemColor,
            modifier = Modifier.size(24.dp)
        )

        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.label,
                color = Color(0xFFFFC107),
                fontSize = 15.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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
fun BottomPreview() {
    val navController = rememberNavController()
    PicoTheme {
        BottomAppBar(navController = navController)
    }
}
