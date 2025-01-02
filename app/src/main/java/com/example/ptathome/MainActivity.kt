package com.example.ptathome

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModelProvider
import com.example.labb3.PtAtHomeApp
import com.example.ptathome.ui.screens.HomeScreen
import com.example.ptathome.ui.theme.PTAtHomeTheme
import com.example.ptathome.ui.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]


        enableEdgeToEdge()
        setContent {
            PTAtHomeTheme {

                // TODO: Part of an experiment to load a html from into the app from the assets folder.
                // Declare a string that contains a url
                /*val mUrl = "file:///android_asset/index.html"

                // Adding a WebView inside AndroidView
                // with layout as full screen
                AndroidView(factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                }, update = {
                    it.loadUrl(mUrl)
                })*/

                //TODO: Keep
                //parseHtml2(
                //    testHetml//htmlText
                //)
                //WebView2(this.baseContext)

                //TODO: Keep
                //HomeScreen(navController, viewModel)
                PtAtHomeApp(viewModel)

                //MyTryOnDropDownMenu2()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBarWithActions() {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = { /* Handle edit! */ },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }

            )
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { /* Handle settings! */ },
                leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = { /* Handle send feedback! */ },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) }
            )
        }
    }
}

@Composable
fun MyTryOnDropDownMenu2(){

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val usernames = listOf(Pair("section 1",MyTryOnDropDownMenu()),Pair("section 2",MyTryOnDropDownMenu()),Pair("section 3",MyTryOnDropDownMenu()))


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = usernames[itemPosition.value].first)
                Image(
                    painter = painterResource(id = R.drawable.round_arrow_drop_down_24),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                usernames.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username.first)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                        })
                }
            }
        }

    }
}

@Composable
fun MyTryOnDropDownMenu(){

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    val usernames = listOf("Alexander", "Isabella", "Benjamin", "Sophia", "Christopher")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = usernames[itemPosition.value])
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                usernames.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            println()
                        })
                }
            }
        }

    }
}
