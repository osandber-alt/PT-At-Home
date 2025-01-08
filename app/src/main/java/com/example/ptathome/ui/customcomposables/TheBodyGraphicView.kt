package com.example.ptathome.ui.customcomposables

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ptathome.R

/**
 *
 * Display a view which is defined inside a XML file.
 *
 * Referenced from this Stack Overflow page:
 * https://stackoverflow.com/questions/77469107/is-it-possible-to-use-both-android-jetpack-compose-views-and-xml-files-together
 *
 * @author Oscar Sandberg
 *
 */

@Composable
fun TheBodyGraphicView(){
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false)
            view
        },
        update = { view ->
            // Update the view
        }
    )
}