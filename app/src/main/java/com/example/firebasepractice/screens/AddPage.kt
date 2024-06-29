package com.example.firebasepractice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun AddPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Box {
        Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF1976D2)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Page",
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}}