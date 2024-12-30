package com.example.ptathome

import android.opengl.GLSurfaceView
import com.example.ptathomepackage.FilamentView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.google.android.filament.Renderer as FilamentRenderer

class FilamentRendererAdapter(private val filamentRenderer: FilamentRenderer, private val filamentView: FilamentView) :
    GLSurfaceView.Renderer {
    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        // Update Filament's viewport or other settings when the surface changes size.
        filamentView.viewport = com.google.android.filament.Viewport(0, 0, 0, 0)
        filamentView.view.viewport = filamentView.viewport
        filamentView.renderCallback?.onResized(0, 0)
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        // Initialize Filament resources here, if needed
        // For example, you might create textures or other Filament objects
        // that depend on the OpenGL context being available.
        // In this basic example, we'll just call the RenderCallback's onSurfaceCreated.
        filamentView.renderCallback?.onSurfaceCreated()
    }

    override fun onDrawFrame(p0: GL10?) {
        if (filamentRenderer.beginFrame(filamentView.surface, filamentView.frameTimeNanos)) {
            filamentRenderer.render(filamentView.view)
            filamentRenderer.endFrame()
        }
    }
}