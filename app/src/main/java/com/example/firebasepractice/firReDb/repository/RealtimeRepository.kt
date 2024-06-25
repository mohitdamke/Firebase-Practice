package com.example.firebasepractice.firReDb.repository

import com.example.firebasepractice.firReDb.RealtimeModelResponse
import com.example.firebasepractice.util.ResultState
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {

    fun insert(
        items: RealtimeModelResponse.RealTimeItems
    ): Flow<ResultState<String>>


    fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>>

    fun delete(
        key: String
    ): Flow<ResultState<String>>

    fun update(
        req: RealtimeModelResponse
    ): Flow<ResultState<String>>
}