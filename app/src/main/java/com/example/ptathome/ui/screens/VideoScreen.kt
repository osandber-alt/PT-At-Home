package com.example.ptathome.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ptathome.R
import com.example.ptathome.externalresources.restresources.TypeOfService
import com.example.ptathome.model.CombinedWikipediaYoutubeData
import com.example.ptathome.ui.customcomposables.YoutubeVideoPlayer
import com.example.ptathome.ui.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *
 * The video screen, which is used to display youtube videos
 *
 * @author Oscar Sandberg
 */

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun VideoScreen(navController: NavHostController, viewModel: ViewModel = hiltViewModel()) {

    val currentDocument by viewModel.currentDocument.collectAsState()
    var currentSize:MutableStateFlow<Int> = MutableStateFlow(-1)
    val isYoutubeServiceComplete by viewModel.isYoutubeServiceComplete.collectAsState()
    val lastService by viewModel.lastService.collectAsState()

    val documentsData by viewModel.theDocumentsAsList.collectAsState()
    val videoTrainingData by viewModel.theTrainingIDsAsList.collectAsState()
    val videoRehabData by viewModel.theRehabIDsAsList.collectAsState()

    var presentedDocumentData by remember { mutableStateOf<List<String>>(emptyList()) }
    var presentedVideoTrainingData by remember { mutableStateOf<List<CombinedWikipediaYoutubeData>>(emptyList()) }
    var presentedVideoRehabData by remember { mutableStateOf<List<CombinedWikipediaYoutubeData>>(emptyList()) }

    val currentVideoTrainingId by viewModel.currentTrainingId.collectAsState()
    val currentVideoRehabId by viewModel.currentRehabId.collectAsState()

    // Populate presented data with view model data
    LaunchedEffect(documentsData,videoTrainingData,videoRehabData) {
        println("launched effect triggered")

        if(isYoutubeServiceComplete){
            viewModel.updateContentListViews()
            if (videoTrainingData.isNotEmpty()) {

                presentedDocumentData = documentsData
                presentedVideoTrainingData = videoTrainingData
                presentedVideoRehabData = videoRehabData

            }
        }

    }

    // Populate presented data with view model data
    LaunchedEffect(Unit) {

        if(isYoutubeServiceComplete){
            viewModel.updateContentListViews()
            if (videoTrainingData.isNotEmpty()) {
                presentedDocumentData = documentsData
                presentedVideoTrainingData = videoTrainingData
                presentedVideoRehabData = videoRehabData

            }
        }

    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = { /* Add a title or leave blank */ },
                actions = { // Add actions in the top bar
                    var expandedDocuments by remember { mutableStateOf(false) }
                    var expandedTrainingVideos by remember { mutableStateOf(false) }
                    var expandedRehabVideos by remember { mutableStateOf(false) }


                    Text(text = "Document")
                    IconButton(onClick = { expandedDocuments = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedDocuments,
                        onDismissRequest = { expandedDocuments = false }
                    ) {

                        presentedDocumentData.forEach { entryList ->
                            DropdownMenuItem(
                                text = { Text(
                                    entryList
                                ) },
                                onClick = {
                                    expandedDocuments = false
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

                    Text(text = "Training")
                    IconButton(onClick = { expandedTrainingVideos = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedTrainingVideos,
                        onDismissRequest = { expandedTrainingVideos = false }
                    ) {
                        presentedVideoTrainingData.forEach { entryList ->
                            Spacer(Modifier.height(16.dp))
                            DropdownMenuItem(
                                text = { Text(entryList.theData.second) },
                                onClick = {
                                }
                            )
                            Box{
                                AsyncImage(
                                    model = entryList.theImageLink,
                                    contentDescription = null,
                                    modifier = Modifier.clickable(onClick = {
                                        expandedTrainingVideos = false
                                        viewModel.modifyCurrentTrainingId(entryList.theData.first)
                                    })
                                )
                            }

                        }
                    }

                    Text(text = "Rehab")
                    IconButton(onClick = { expandedRehabVideos = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_drop_down_24), // Replace with your menu icon
                            contentDescription = "Entries"
                        )
                    }

                    DropdownMenu(
                        expanded = expandedRehabVideos,
                        onDismissRequest = { expandedRehabVideos = false }
                    ) {
                        presentedVideoRehabData.forEach { entryList ->
                            DropdownMenuItem(
                                text = { Text(entryList.theData.second) },
                                onClick = {
                                }
                            )

                            Box{
                                AsyncImage(
                                    model = entryList.theImageLink,
                                    contentDescription = null,
                                    modifier = Modifier.clickable(onClick = {
                                        expandedRehabVideos = false
                                        viewModel.modifyCurrentRehabId(entryList.theData.first)
                                    })
                                )
                            }

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
                        isYoutubeServiceComplete && lastService == TypeOfService.Youtube ||
                        (isYoutubeServiceComplete && lastService == TypeOfService.Both)
                        ){

                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Current Document = ${currentDocument.getName()}",
                            color = Color.DarkGray
                        )

                        if(currentVideoTrainingId!= ""){
                            Spacer(Modifier.height(16.dp))

                            Text(text = "Training video")

                            Spacer(Modifier.height(16.dp))
                            Box(modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                                currentSize.value = it.size.width
                            }){
                                YoutubeVideoPlayer(
                                    videoId = currentVideoTrainingId
                                //videoId = currentDocument.getTrainingVideoIdByIndex(0)
                                //"bHQqvYy5KYo" Keep for now!!!
                                )
                            }}

                        if(currentVideoRehabId!=""){
                            Spacer(Modifier.height(16.dp))
                            Text(text = "Rehab video")
                            Spacer(Modifier.height(16.dp))

                            Box(modifier = Modifier.fillMaxWidth().onGloballyPositioned {
                                currentSize.value = it.size.width
                            })
                            {
                                YoutubeVideoPlayer(
                                    videoId = currentVideoRehabId
                                    //"bHQqvYy5KYo" Keep for now!!!
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
