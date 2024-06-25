package com.example.firebasepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isInsert = remember {
                mutableStateOf(false)
            }
            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = { isInsert.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )

                }
            }) {
                RealtimeScreen(isInsert, it)
            }
        }
    }
}