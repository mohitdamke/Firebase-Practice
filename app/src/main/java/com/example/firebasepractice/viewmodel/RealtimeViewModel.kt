package com.example.firebasepractice.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasepractice.ItemState
import com.example.firebasepractice.firReDb.RealtimeModelResponse
import com.example.firebasepractice.firReDb.repository.RealtimeRepository
import com.example.firebasepractice.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val realtimeRepository: RealtimeRepository
) : ViewModel() {

    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    fun insert(items: RealtimeModelResponse.RealTimeItems) = realtimeRepository.insert(items)

    init {
        viewModelScope.launch {
            realtimeRepository.getItems().collect {
                when (it) {
                    is ResultState.Failure -> {
                        _res.value = ItemState(error = it.message.toString())
                    }

                    ResultState.Loading -> {
                        _res.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _res.value = ItemState(item = it.data)
                    }
                }
            }
        }
    }

    fun delete(key: String) = realtimeRepository.delete(key)

    fun update(items: RealtimeModelResponse) = realtimeRepository.update(items)


}