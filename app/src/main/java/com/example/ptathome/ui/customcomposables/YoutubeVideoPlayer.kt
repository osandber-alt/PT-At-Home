package com.example.ptathome.ui.customcomposables

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A view which is used to display a youtube video from a video id.
 *
 * The resource used to get this to work is from the following Stack Overflow Article:
 * https://stackoverflow.com/questions/76538826/how-to-implement-youtube-iframe-player-api-in-android-using-kotlin-and-jetpack-c
 *
 * @author Oscar Sandberg
 *
 */

@Composable
fun YoutubeVideoPlayer(videoId: String,
//                       width: Int
) {
    val webView = WebView(LocalContext.current).apply {
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        webViewClient = WebViewClient()
    }

    val htmlData = getHTMLData(videoId
        //    , width = width
    )

    Column(Modifier.fillMaxWidth()) {

        AndroidView(factory = { webView }) { view ->
            view.loadDataWithBaseURL(
                "https://www.youtube.com",
                htmlData,
                "text/html",
                "UTF-8",
                null
            )
        }
       /* Button(onClick = {
            webView.loadUrl("javascript:playVideo();")
        }) {
            Text(text = "Play Video")
        }
        Button(onClick = {
            webView.loadUrl("javascript:pauseVideo();")
        }) {
            Text(text = "Pause Video")
        }*/
    }
}

fun getHTMLData(videoId: String, height: Int = 450, width:Int = 650): String {
    return """
        <html>
        
            <body style="margin:0px;padding:0px;">
               <div id="player"></div>
                <script>
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '$height',
                            width: '$width',
                            videoId: '$videoId',
                            playerVars: {
                                'playsinline': 1
                            },
                            events: {
                                'onReady': onPlayerReady
                            }
                        });
                    }
                    function onPlayerReady(event) {
                     player.playVideo();
                        // Player is ready
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                    
                    
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """.trimIndent()
}
