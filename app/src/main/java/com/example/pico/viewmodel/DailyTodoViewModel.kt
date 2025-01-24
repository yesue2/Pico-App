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


class DailyTodoViewModel(private val repository: DailyTodoRepository) : ViewModel() {

    // StateFlow로 현재 선택된 Todo 데이터를 관리
    private val _selectedTodo = MutableStateFlow<DailyTodoEntity?>(null)
    val selectedTodo: StateFlow<DailyTodoEntity?> get() = _selectedTodo

    // 모든 Todos 리스트
    val todos: StateFlow<List<DailyTodoEntity>> =
        repository.getAllTodos()
            .map { it } // 필요 시 추가 처리 가능
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 모든 Todos 삭제
    fun deleteAllTodos() {
        viewModelScope.launch {
            repository.deleteAllTodos()
        }
    }

    // Todo 삽입
    fun insertDaily(todo: DailyTodoEntity) {
        viewModelScope.launch {
            repository.insertDaily(todo)

            val todos = repository.getAllTodos()
            todos.collect {
                Log.d("DailyTodo", "Saved Todo: $it")
            }
        }
    }

    // Todo 업데이트
    fun update(todo: DailyTodoEntity) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    // Todo ID로 데이터 가져오기
    fun loadTodoById(todoId: Int) {
        viewModelScope.launch {
            val todo = repository.getTodoById(todoId) // suspend 함수 호출
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
