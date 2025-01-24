package com.example.pico.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.repository.DailyTodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class DailyTodoViewModel(private val repository: DailyTodoRepository) : ViewModel() {
    fun deleteAllTodos() {
        viewModelScope.launch {
            repository.deleteAllTodos() // 데이터 삭제
        }
    }

    fun insertDaily(todo: DailyTodoEntity) {
        viewModelScope.launch {
            repository.insertDaily(todo)

            val todos = repository.getAllTodos()
            todos.collect {
                Log.d("DailyTodo", "Saved Todo: $it")
            }
        }
    }

    val todos: StateFlow<List<DailyTodoEntity>> =
        repository.getAllTodos()
            .map { it } // 필요 시 추가 처리 가능
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
