package com.yesue2.pico.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButtonGroup(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ToggleButton(
            text = "할 일",
            isSelected = selectedTab == "할 일",
            onClick = { onTabSelected("할 일") },
            modifier = Modifier.weight(1f) // ✅ 균등한 너비 설정
        )

        ToggleButton(
            text = "이달의 목표",
            isSelected = selectedTab == "이달의 목표",
            onClick = { onTabSelected("이달의 목표") },
            modifier = Modifier.weight(1f) // ✅ 균등한 너비 설정
        )
    }
}

