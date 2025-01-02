package com.example.ptathome.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ptathome.ui.viewmodel.ViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ptathome.externalresources.restresources.TypeOfService
import com.example.ptathome.ui.customcomposables.YoutubeVideoPlayer
import kotlinx.coroutines.flow.MutableStateFlow




@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WikipediaScreen(navController: NavHostController, viewModel: ViewModel = hiltViewModel()) {

    val currentDocument by viewModel.currentDocument.collectAsState()
    var currentSize:MutableStateFlow<Int> = MutableStateFlow(-1)
    val isComplete by viewModel.isComplete.collectAsState()
    val isComplete2 by viewModel.isComplete2.collectAsState()
    val lastService by viewModel.lastService.collectAsState()

    var bodyPartSearch by remember { mutableStateOf("") }    // Longitude state

    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Wikipedia Screen",
                        color = Color.DarkGray
                    )

                    Spacer(Modifier.height(16.dp))


                    if(isComplete){
                        Text(
                            text = "Current Document = ${currentDocument.getName()}",
                            color = Color.DarkGray
                        )
                        Spacer(Modifier.height(16.dp))

                        Box(modifier = Modifier.height(120.dp)
                            .verticalScroll(rememberScrollState())){
                            Text(
                                text = currentDocument.getSummary() ,
                                color = Color.DarkGray
                            )
                        }
                    }


                    Spacer(Modifier.height(16.dp))

                    Button(onClick = { navController.navigateUp() }) {
                        Text(
                            text = "Go back",
                            color = Color.White
                        )
                    }

                    if(isComplete){
                        Box(modifier = Modifier.height(120.dp)
                            .verticalScroll(rememberScrollState())){
                            Text(
                                text =  currentDocument.getAllSections("section").toString(),
                                color = Color.DarkGray
                            )
                        }
                    }

                }
            }
        }
    }
}
