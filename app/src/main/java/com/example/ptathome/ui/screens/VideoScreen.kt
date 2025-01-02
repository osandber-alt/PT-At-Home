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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ptathome.ui.viewmodel.ViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ptathome.R
import com.example.ptathome.externalresources.restresources.TypeOfService
import com.example.ptathome.ui.customcomposables.YoutubeVideoPlayer
import kotlinx.coroutines.flow.MutableStateFlow



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun VideoScreen(navController: NavHostController, viewModel: ViewModel = hiltViewModel()) {

    val currentDocument by viewModel.currentDocument.collectAsState()
    var currentSize:MutableStateFlow<Int> = MutableStateFlow(-1)
    val isComplete2 by viewModel.isComplete2.collectAsState()
    val lastService by viewModel.lastService.collectAsState()

    val documentsData by viewModel.theDocumentsAsList.collectAsState()
    val videoTrainingData by viewModel.theTrainingIDsAsList.collectAsState()
    val videoRehabData by viewModel.theRehabIDsAsList.collectAsState()

    var data1 by remember { mutableStateOf<List<String>>(emptyList()) }
    var data2 by remember { mutableStateOf<List<String>>(emptyList()) }
    var data3 by remember { mutableStateOf<List<String>>(emptyList()) }

    var videoIndex by remember {  mutableStateOf("") }
    var videoIndex2 by remember {  mutableStateOf("") }

    LaunchedEffect(documentsData,videoTrainingData,videoRehabData) {
        // Update the graph's data whenever listOfEntries changes
        println("launched effect triggered")

        if(isComplete2){
            viewModel.updateContentListViews()
            if (videoTrainingData.isNotEmpty()) {

                data1 = documentsData
                data2 = videoTrainingData
                data3 = videoRehabData

                //println("LE listOfStrings data: $data.")
            }
        }

    }

    LaunchedEffect(Unit) {

        if(isComplete2){
            viewModel.updateContentListViews()
            if (videoTrainingData.isNotEmpty()) {

                data1 = documentsData
                data2 = videoTrainingData
                data3 = videoRehabData

                //println("LE listOfStrings data: $data.")
            }
        }

        /*if (videoTrainingData.isNotEmpty()) {
            //videoTrainingData = viewModel.currentDocument.value.getTrainingVideoId()
            //videoRehabData = viewModel.currentDocument.value.getRehabVideoId()
            data1 = documentsData
            data2 = videoTrainingData
            data3 = videoRehabData

            //println("LE Unit data: $data")
        }*/
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Add a title or leave blank */ },
                actions = { // Add actions in the top bar
                    var expanded by remember { mutableStateOf(false) }
                    var expanded2 by remember { mutableStateOf(false) }
                    var expanded3 by remember { mutableStateOf(false) }


                    Text(text = "Data")
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                        data1.forEach { entryList ->
                            DropdownMenuItem(
                                text = { Text(
                                    entryList
                                    //viewModel.getListOfDocuments()[viewModel.getListOfDocuments().indexOf(entryList)].getName()
                                ) },
                                onClick = {
                                    expanded = false
                                    for(i in viewModel.getListOfDocuments()){
                                        if(i.getName() == entryList){
                                            viewModel.setNewDocument(i)
                                            break
                                        }
                                    }
                                }
                            )
                        }
                    }

                    IconButton(onClick = { expanded2 = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded2,
                        onDismissRequest = { expanded2 = false }
                    ) {
                        data2.forEach { entryList ->
                            DropdownMenuItem(
                                text = { Text(entryList) },
                                onClick = {
                                    expanded2 = false
                                    videoIndex = entryList
                                }
                            )
                        }
                    }

                    IconButton(onClick = { expanded3 = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded3,
                        onDismissRequest = { expanded3 = false }
                    ) {
                        data3.forEach { entryList ->
                            DropdownMenuItem(
                                text = { Text(entryList) },
                                onClick = {
                                    expanded3 = false
                                    videoIndex2 = entryList
                                }
                            )
                        }
                    }
                }
            )
        }
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
                        text = "Video Screen",
                        color = Color.DarkGray
                    )

                    if(
                        isComplete2 && lastService == TypeOfService.Youtube ||
                        (isComplete2 && lastService == TypeOfService.Both)
                        ){

                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Current Document = ${currentDocument.getName()}",
                            color = Color.DarkGray
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(text = "training video")

                        Spacer(Modifier.height(16.dp))

                        Box(modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                            currentSize.value = it.size.width
                        }){
                            if(videoIndex!= ""){
                            YoutubeVideoPlayer(
                                videoId = videoIndex
                                //videoId = currentDocument.getTrainingVideoIdByIndex(0)
                                //"bHQqvYy5KYo" Keep for now!!!
                                //,currentSize.value
                            )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(text = "Rehab video")

                        Spacer(Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                            currentSize.value = it.size.width
                        }){
                            if(videoIndex2!=""){
                            YoutubeVideoPlayer(
                                videoId = videoIndex2
                                //"bHQqvYy5KYo" Keep for now!!!
                                //,currentSize.value
                            )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))


                    Button(onClick = { navController.navigateUp() }) {
                        Text(
                            text = "Go back",
                            color = Color.White
                        )
                    }


                }
            }
        }
    }
}
