package com.example.firebasepractice.firReDb

data class RealtimeModelResponse(
    val item: RealTimeItems? = null, val key: String? = null
) {
    data class RealTimeItems(
        val title: String? = null,
        val description: String? = null,
    )
}
