package com.example.ptathome.ui.customcomposables

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ptathome.R
@Composable
fun TheBodyGraphicView(){
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false)
            //val textView = view.findViewById<TextView>(R.id.text)

            // do whatever you want...
            view // return the view
        },
        update = { view ->
            // Update the view
        }
    )
}