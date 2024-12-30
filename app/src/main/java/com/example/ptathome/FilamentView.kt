package com.example.ptathomepackage

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import android.view.Surface
import com.example.ptathome.FilamentRendererAdapter
import com.google.android.filament.Engine
import com.google.android.filament.Renderer
import com.google.android.filament.Scene
import com.google.android.filament.SwapChain
import com.google.android.filament.View
import com.google.android.filament.Viewport
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.google.android.filament.Renderer as FilamentRenderer


class FilamentView(context: Context) : GLSurfaceView(context) {

    lateinit var engine: Engine
    private lateinit var filamentRenderer: FilamentRenderer // Use Filament
    lateinit var renderer: Renderer
    lateinit var surface: SwapChain
    lateinit var view: View
    var viewport = Viewport(0, 0, 0, 0)
    var scene: Scene? = null
    var renderCallback: RenderCallback? = null

    private val renderCallbackImpl = object : RenderCallback {
        override fun onNativeWindowChanged(surface: SwapChain) {
            // Handle native window change
        }

        override fun onDetachedFromSurface() {
            // Handle detachment from surface
        }

        override fun onResized(width: Int, height: Int) {
            // Handle resize event
        }

        // ... other RenderCallback methods ...
    }

    init {
        setEGLContextClientVersion(3)
        renderCallback = renderCallbackImpl

        // Create Filament Engine and Renderer
        engine = Engine.create()
        filamentRenderer = engine.createRenderer()

        // Create and set the FilamentRendererAdapter
        val rendererAdapter = FilamentRendererAdapter(filamentRenderer, this)
        setRenderer(rendererAdapter)
    }



    init {
        setEGLContextClientVersion(3)
        renderCallback = renderCallbackImpl // Assign the RenderCallback implementation

        setRenderer(object : Renderer {
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                // Filament initialization
                renderCallback?.onSurfaceCreated() // Call RenderCallback method
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                // Handle surface change
                renderCallback?.onResized(width, height) // Call RenderCallback method
            }

            override fun onDrawFrame(gl: GL10?) {
                // Filament rendering
            }
        })
    }


    interface RenderCallback {
        fun onNativeWindowChanged(surface: SwapChain) {}
        fun onDetachedFromSurface() {}
        fun onSurfaceCreated() {}
        fun onSurfaceDestroyed() {}
        fun onResized(width: Int, height: Int) {}
    }

    private var startTime = System.nanoTime()
    val frameTimeNanos: Long
        get() {
            val now = System.nanoTime()
            val time = now - startTime
            startTime = now
            return time
        }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }
}