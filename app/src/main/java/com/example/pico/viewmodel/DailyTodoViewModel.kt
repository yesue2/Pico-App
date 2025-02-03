package com.example.pico.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.repository.DailyTodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar



class DailyTodoViewModel(private val repository: DailyTodoRepository) : ViewModel() {

    // StateFlow로 현재 선택된 Todo 데이터를 관리
    private val _selectedTodo = MutableStateFlow<DailyTodoEntity?>(null)
    val selectedTodo: StateFlow<DailyTodoEntity?> get() = _selectedTodo

    // 실행 전인 모든 Todos 리스트
    val allDailyTodos: StateFlow<List<DailyTodoEntity>> =
        repository.getAllDailyTodos()
            .map { it } // 필요 시 추가 처리 가능
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 실행 후인 모든 Todos 리스트
    val completedDailyTodos: StateFlow<List<DailyTodoEntity>> =
        repository.getCompletedDailyTodos()
            .map { it } // 필요 시 추가 처리 가능
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // 오늘 할 일 리스트
    private val _todayTodos = MutableStateFlow<List<DailyTodoEntity>>(emptyList())
    val todayTodos: StateFlow<List<DailyTodoEntity>> get() = _todayTodos

    // 오늘 할 일 가져오기
    fun loadTodayTodos() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfDay = calendar.timeInMillis

            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfDay = calendar.timeInMillis

            repository.getTodayTodos(startOfDay, endOfDay).collect { todos ->
                _todayTodos.value = todos
            }
        }
    }

    // 모든 Todos 삭제
    fun deleteAllTodos() {
        viewModelScope.launch {
            repository.deleteAllTodos()
        }
    }

    // 특정 ID로 Todo 삭제
    fun deleteDailyTodoById(todoId: Int) {
        viewModelScope.launch {
            repository.deleteDailyTodoById(todoId)
        }
    }

    // Todo 삽입
    fun insertDaily(todo: DailyTodoEntity) {
        viewModelScope.launch {
            repository.insertDaily(todo)

            val todos = repository.getAllDailyTodos()
            todos.collect {
                Log.d("DailyTodo", "Saved Todo: $it")
            }
        }
    }

    // Todo 업데이트
    fun updateDaily(todo: DailyTodoEntity) {
        viewModelScope.launch {
            repository.updateDaily(todo)
        }
    }

    // Todo ID로 데이터 가져오기
    fun loadDailyTodoById(todoId: Int) {
        viewModelScope.launch {
            val todo = repository.getDailyTodoById(todoId)
            _selectedTodo.value = todo
        }
    }
}


class DailyTodoViewModelFactory(private val repository: DailyTodoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyTodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyTodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
