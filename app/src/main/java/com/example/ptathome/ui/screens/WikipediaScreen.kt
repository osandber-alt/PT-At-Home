package com.example.ptathome.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ptathome.ui.viewmodel.ViewModel

/**
 *
 * The wikipedia screen, which is used to display the text from a wikipedia article
 *
 * @author Oscar Sandberg
 */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun WikipediaScreen(navController: NavHostController, viewModel: ViewModel = hiltViewModel()) {

    val currentDocument by viewModel.currentDocument.collectAsState()
    val isWikipediaServiceComplete by viewModel.isWikipediaServiceComplete.collectAsState()

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


                    if(isWikipediaServiceComplete){
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

                    if(isWikipediaServiceComplete){
                        Box(modifier = Modifier.fillMaxHeight()
                            .verticalScroll(rememberScrollState())){
                            Column {
                                for (i in viewModel.currentDocument.value.getDocument()){
                                    println(i.second)
                                    if(i.second == "h1"){
                                        Text(
                                            text =  i.first,
                                            color = Color.DarkGray,
                                            fontSize = 50.sp,
                                            lineHeight = 50.sp
                                        )
                                    }

                                    else if(i.second == "h2"){
                                        Text(
                                            text =  i.first,
                                            color = Color.DarkGray,
                                            fontSize = 50.sp,
                                            lineHeight = 50.sp
                                        )
                                    }

                                    else if(i.second == "h3"){
                                        Text(
                                            text =  i.first,
                                            color = Color.DarkGray,
                                            fontSize = 50.sp,
                                            lineHeight = 50.sp
                                        )
                                    }

                                    else if(i.second == "h4"){
                                        Text(
                                            text =  i.first,
                                            color = Color.DarkGray,
                                            fontSize = 50.sp,
                                            lineHeight = 50.sp
                                        )
                                    }

                                    else{
                                        Text(
                                            text =  i.first,
                                            color = Color.DarkGray,
                                            fontSize = 10.sp,
                                            lineHeight = 25.sp
                                        )
                                        Spacer(Modifier.height(16.dp))
                                    }
                                }
                            }
                            //Text(
                            //    text =  currentDocument.getAllSections("section").toString(),
                            //    color = Color.DarkGray
                            //)
                        }
                    }
                }
            }
        }
    }
}
