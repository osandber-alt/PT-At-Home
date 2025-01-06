package com.example.ptathome

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent

class TouchGLSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    private val renderer: ObjRenderer = ObjRenderer(context)
    private var previousX = 0f
    private var previousY = 0f

    init {
        setEGLContextClientVersion(1) // Använd OpenGL ES 1.0
        setRenderer(renderer)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.x - previousX
                val deltaY = event.y - previousY

                // Uppdatera rotation baserat på rörelse
                renderer.setRotation(
                    renderer.angleX + deltaY * 0.5f, // Roterar kring X-axeln (upp/ned)
                    renderer.angleY + deltaX * 0.5f  // Roterar kring Y-axeln (vänster/höger)
                )
            }
        }
        previousX = event.x
        previousY = event.y
        return true
    }
}
