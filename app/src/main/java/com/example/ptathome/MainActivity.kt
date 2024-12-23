package com.example.ptathome

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.ptathome.externalresources.htmlparser.parseHtml
import com.example.ptathome.ui.screens.HomeScreen
import com.example.ptathome.ui.screens.htmlText
import com.example.ptathome.ui.screens.testHetml
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
                parseHtml(
                    testHetml//htmlText
                )
                //WebView2(this.baseContext)

                //TODO: Keep
                //HomeScreen(viewModel)
            }
        }
    }
}
