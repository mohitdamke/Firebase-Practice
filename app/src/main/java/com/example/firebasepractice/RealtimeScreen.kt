package com.example.firebasepractice

import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firebasepractice.firReDb.RealtimeModelResponse
import com.example.firebasepractice.util.ResultState
import com.example.firebasepractice.viewmodel.RealtimeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RealtimeScreen(
    isInsert: MutableState<Boolean>,
    paddingValues: PaddingValues,
    viewModel: RealtimeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    val res = viewModel.res.value
    val isDialog = remember {
        mutableStateOf(
            false
        )
    }

    if (isInsert.value) {
        AlertDialog(onDismissRequest = { isInsert.value = false }, text = {
            Column {
                OutlinedTextField(value = title, onValueChange = {
                    title = it
                }, placeholder = { Text(text = "Title") })
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(value = description, onValueChange = {
                    description = it
                }, placeholder = { Text(text = "Description") })
            }
        }, confirmButton = {
            IconButton(onClick = {
                scope.launch(Dispatchers.Main) {
                    viewModel.insert(
                        RealtimeModelResponse.RealTimeItems(
                            title = title, description = description
                        )
                    ).collect {
                        when (it) {
                            is ResultState.Failure -> {
                                Log.d("TAG", "RealtimeScreen: $title")
                                isDialog.value = false
                                isInsert.value = false
                                Toast.makeText(
                                    context, it.message.toString(), Toast.LENGTH_SHORT
                                ).show()

                            }


                            is ResultState.Success -> {
                                Log.d("TAG", "Success: $title")
                                isDialog.value = false
                                Toast.makeText(
                                    context, it.data, Toast.LENGTH_SHORT
                                ).show()

                            }

                            ResultState.Loading -> {
                                Log.d("TAG", "Loading: $title")
                                isDialog.value = true
                                isInsert.value = false
                                Toast.makeText(
                                    context, it.toString(), Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }
            }) {
                Text(text = "Save")
            }
        })
    }

    if (res.item.isNotEmpty()) {
        Text(text = "raseesf")
        LazyColumn {
            items(res.item, key = {
                it.key!!
            }) { res ->
                EachRow(itemState = res.item!!)
            }
        }
    }
    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = res.error)
        }
    }

}

@Composable
fun EachRow(itemState: RealtimeModelResponse.RealTimeItems) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = itemState.title ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
                Text(
                    text = itemState.description ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
            }
        }
    }
}