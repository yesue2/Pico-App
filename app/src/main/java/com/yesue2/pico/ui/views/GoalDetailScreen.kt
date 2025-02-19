package com.yesue2.pico.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yesue2.pico.R
import com.yesue2.pico.data.monthly.MonthlyGoalEntity
import com.yesue2.pico.ui.components.CardBox
import com.yesue2.pico.ui.components.ProgressGridContainer
import com.yesue2.pico.ui.components.SummitButton
import com.yesue2.pico.ui.components.TopAppBarDetail
import com.yesue2.pico.ui.theme.BackBlue
import com.yesue2.pico.ui.theme.BackGreen
import com.yesue2.pico.ui.theme.BackPink
import com.yesue2.pico.ui.theme.BackYellow
import com.yesue2.pico.viewmodel.MonthlyGoalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GoalDetailScreen(
    navController: NavController,
    goalId: Int,
    viewModel: MonthlyGoalViewModel = hiltViewModel()
) {
    LaunchedEffect(goalId) {
        viewModel.loadGoalById(goalId)
    }

    val goal = viewModel.selectedGoal.collectAsState().value

    // 자동 진행 방식 업데이트 실행
    LaunchedEffect(goal?.trackingMethod) {
        if (goal != null && goal.trackingMethod in listOf("매일 1 자동 증가", "목표에 맞춰 자동 계산")) {
            viewModel.updateProgressAutomatically(goal)
        }
    }

    Scaffold(
        topBar = {
            TopAppBarDetail(
                screen = "Detail",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
        ) {
            item {
                goal?.let { DetailGoalListSection(it, navController) } ?: Text(
                    text = "목표 정보를 불러오는 데 실패했습니다. \n뒤로가기를 눌러주세요",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun DetailGoalListSection(
    goal: MonthlyGoalEntity,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "목표 자세히 보기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailGoalForm(goal, navController)
    }
}

@Composable
fun DetailGoalForm(
    goal: MonthlyGoalEntity,
    navController: NavController,
    viewModel: MonthlyGoalViewModel = hiltViewModel()
) {
    CardBox(txt = "이번 달 목표, 얼마나 해냈을까요?") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            TitleAndDate(goal)

            Spacer(modifier = Modifier.height(20.dp))

            ProgressMessage(goal)

            Spacer(modifier = Modifier.height(20.dp))

            // "진행률 표시 없음"이 아닐 경우 자동 진행도 증가 실행
            if (goal.trackingMethod in listOf("매일 1 자동 증가", "목표에 맞춰 자동 계산")) {
                LaunchedEffect(goal.trackingMethod) {
                    viewModel.updateProgressAutomatically(goal)
                }
            }

            // ✅ "진행률 기록 안 하기"가 아닐 경우에만 진행도 UI 표시
            if (goal.trackingMethod != "진행률 기록 안 하기") {
                ProgressGridContainer(goal, viewModel)
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 완료 여부와 버튼
            GoalCompletionSection(goal, navController)
        }
    }
}

@Composable
fun TitleAndDate(goal: MonthlyGoalEntity) {
    val dDay = calculateDday(goal.endDate)

    val (iconResId, backgroundColor) = when (goal.category) {
        1 -> R.drawable.ic_company to BackBlue
        2 -> R.drawable.ic_personal to BackPink
        3 -> R.drawable.ic_shopping to BackYellow
        4 -> R.drawable.ic_study to BackGreen
        5 -> R.drawable.ic_friend to BackBlue
        6 -> R.drawable.ic_invest to BackYellow
        7 -> R.drawable.ic_health to BackGreen
        8 -> R.drawable.ic_hobby to BackPink
        9 -> R.drawable.ic_housework to BackPink
        10 -> R.drawable.ic_etc to BackYellow
        else -> R.drawable.ic_etc to Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = backgroundColor, shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = goal.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "${formatDate(goal.startDate)}부터\n${formatDate(goal.endDate)}까지",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                )
            }
        }
        Text(
            text = dDay,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProgressMessage(goal: MonthlyGoalEntity) {
    val remaining = goal.goalAmount - goal.progress

    // 오늘 날짜 (밀리초 단위)와 시작 날짜 차이 계산
    val currentTime = System.currentTimeMillis()
    val elapsedDays = ((currentTime - goal.startDate) / (1000 * 60 * 60 * 24)).toInt() + 1

    Text(
        text = when {
            elapsedDays <= 0 -> "아직 목표 시작 전이에요! 🌱\n곧 시작될 챌린지를 기대해봐요!"
            remaining > 0 -> "${elapsedDays}일 동안 벌써 ${goal.progress}${goal.unit} 완료했어요!\n남은 ${remaining}${goal.unit}까지 모두 달성해봐요! 화이팅✨"
            else -> "🎉 목표를 모두 달성했어요! 대단해요! 🎉"
        },
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
}


@Composable
fun ProgressGrid(currentProgress: Int, goalAmount: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(goalAmount) { index ->
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = if (index < currentProgress) MaterialTheme.colorScheme.primary else Color(
                                0xFFFFE582
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                )
            }
        }
    }
}


@Composable
fun GoalCompletionSection(
    goal: MonthlyGoalEntity,
    navController: NavController,
    viewModel: MonthlyGoalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isSwitchChecked by remember { mutableStateOf(goal.isCompleted) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "목표를 모두 달성했나요?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Switch(
                checked = isSwitchChecked,
                onCheckedChange = { isCompleted ->
                    isSwitchChecked = isCompleted
                    viewModel.completeGoal(goal, isCompleted)
                    val updatedProgress = if (isCompleted && goal.trackingMethod == "진행률 기록 안 하기") {
                        goal.goalAmount // ✅ 완료 시 목표 수량으로 진행률 변경
                    } else if (!isCompleted && goal.trackingMethod == "진행률 기록 안 하기") {
                        0 // ✅ 미완료 시 진행률을 다시 0으로
                    } else {
                        goal.progress // ✅ 다른 방식의 경우 기존 값 유지
                    }

                    val updatedGoal = goal.copy(
                        isCompleted = isCompleted,
                        progress = updatedProgress
                    )

                    viewModel.updateGoal(updatedGoal)
                },

                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White, // 활성 상태 버튼(원) 색상
                    uncheckedThumbColor = MaterialTheme.colorScheme.tertiary, // 비활성 상태 버튼(원) 색상
                    checkedTrackColor = Color(0xFFFFD54F), // 활성 상태 트랙 색상 (노란색)
                    uncheckedTrackColor = Color(0xFFDFEAF2), // 비활성 상태 트랙 색상 (연파란색)
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                )
            )
            Text(
                text = "네! 목표를 완벽히 해냈어요! \uD83C\uDF89",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 30.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "이 목표 삭제하기",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        viewModel.deleteGoalById(goal.id)
                        Toast.makeText(
                            context,
                            "목표가 삭제되었어요! 다음 목표도 화이팅! \uD83D\uDCAA",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            SummitButton(content = "Back to list") {
                navController.popBackStack()
            }
        }
    }
}

// 날짜 포맷 변환 함수
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun calculateDday(endDate: Long): String {
    val currentDate = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
    val endDateDays = endDate / (1000 * 60 * 60 * 24)
    val daysRemaining = (endDateDays - currentDate).toInt()

    return when {
        daysRemaining > 0 -> "D-$daysRemaining"
        daysRemaining == 0 -> "오늘 마감"
        else -> "기한 지남"
    }
}