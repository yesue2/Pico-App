package com.yesue2.pico.viewmodel

import com.yesue2.pico.data.monthly.MonthlyGoalEntity
import com.yesue2.pico.repository.MonthlyGoalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MonthlyGoalViewModelTest {

    private lateinit var viewModel: MonthlyGoalViewModel
    private val repository = mockk<MonthlyGoalRepository>()

    @Before
    fun setup() {
        viewModel = MonthlyGoalViewModel(repository)
    }

    @Test
    fun testInsertGoal() = runTest {
        val goal = MonthlyGoalEntity(
            id = 1,
            title = "책 30권 읽기",
            category = 1,
            importance = 5,
            isCompleted = false,
            goalAmount = 30,
            unit = "권",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30), // 30일 후
            progress = 0,
            trackingMethod = "manual"
        )

        viewModel.insertGoal(goal)

        coVerify { repository.insertGoal(goal) } // Repository의 insertGoal이 호출되었는지 확인
    }

    @Test
    fun testUpdateGoal() = runTest {
        val updatedGoal = MonthlyGoalEntity(
            id = 1,
            title = "책 30권 읽기",
            category = 1,
            importance = 5,
            isCompleted = false,
            goalAmount = 30,
            unit = "권",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30),
            progress = 10,
            trackingMethod = "manual"
        )

        coEvery { repository.updateGoal(updatedGoal) } returns Unit

        viewModel.updateGoal(updatedGoal)

        coVerify(exactly = 1) { repository.updateGoal(updatedGoal) }
    }

    @Test
    fun testDeleteGoal() = runTest {
        val goal = MonthlyGoalEntity(
            id = 1,
            title = "책 30권 읽기",
            category = 1,
            importance = 5,
            isCompleted = false,
            goalAmount = 30,
            unit = "권",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30),
            progress = 10,
            trackingMethod = "manual"
        )

        coEvery { repository.deleteGoal(goal) } returns Unit

        viewModel.deleteGoal(goal)

        coVerify(exactly = 1) { repository.deleteGoal(goal) }
    }

    @Test
    fun testLoadGoalById() = runTest {
        val goal = MonthlyGoalEntity(
            id = 1,
            title = "책 30권 읽기",
            category = 1,
            importance = 5,
            isCompleted = false,
            goalAmount = 30,
            unit = "권",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30),
            progress = 10,
            trackingMethod = "manual"
        )

        coEvery { repository.getGoalById(1) } returns goal

        viewModel.loadGoalById(1)

        assertEquals(goal, viewModel.selectedGoal.value)

        coVerify(exactly = 1) { repository.getGoalById(1) }
    }
}
