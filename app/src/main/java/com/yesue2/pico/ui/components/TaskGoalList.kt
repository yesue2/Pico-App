package com.yesue2.pico.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yesue2.pico.R
import com.yesue2.pico.data.monthly.MonthlyGoalEntity

@Composable
fun TaskGoalList(comment: String, goals: List<MonthlyGoalEntity>, navController: NavController) {
    CardBox(txt = comment) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column() {
                goals.forEach { goal ->
                    val (iconRes, backgroundColor) = getCategoryIconAndColor(goal.category)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("detailGoal/${goal.id}")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextBox(
                            title = goal.title,
                            detail = "${goal.progress} / ${goal.goalAmount}${goal.unit}",
                            icon = painterResource(id = iconRes),
                            backgroundColor = backgroundColor
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_detail),
                            contentDescription = "상세보기 버튼",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

