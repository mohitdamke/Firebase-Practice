package com.example.firebasepractice

import com.example.firebasepractice.firReDb.RealtimeModelResponse

data class ItemState(
    val item : List<RealtimeModelResponse> = emptyList(),
    val isLoading : Boolean = false,
    val error : String = ""
)
