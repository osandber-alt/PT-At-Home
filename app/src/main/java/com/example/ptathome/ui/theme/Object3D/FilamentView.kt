package com.example.ptathome.ui.theme.Object3D
/*
import android.content.Context
import android.os.Bundle
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import com.google.android.filament.*
import com.google.android.filament.android.*
import com.google.android.filament.gltfio.AssetLoader
import com.google.android.filament.gltfio.ResourceLoader
import com.google.android.filament.utils.*

class MainActivity : ComponentActivity() {

    private lateinit var surfaceView: SurfaceView
    private lateinit var filamentEngine: Engine
    private lateinit var renderer: Renderer
    private lateinit var scene: Scene
    private lateinit var view: View
    private lateinit var camera: Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        surfaceView = SurfaceView(this)
        setContentView(surfaceView)

        // Initiera Filament
        filamentEngine = Engine.create()
        renderer = filamentEngine.createRenderer()
        scene = filamentEngine.createScene()

        // Skapa kamera och ljus
        camera = filamentEngine.createCamera()
        view = filamentEngine.createView()
        view.scene = scene
        view.camera = camera

        // Lägg till modellen
        loadModel("scene.gltf")

        // Rendera
        surfaceView.holder.addCallback(object : android.view.SurfaceHolder.Callback {
            override fun surfaceCreated(holder: android.view.SurfaceHolder) {
                renderer.setSurface(holder.surface)
            }

            override fun surfaceChanged(holder: android.view.SurfaceHolder, format: Int, width: Int, height: Int) {
                camera.setProjection(45.0, width.toDouble() / height, 0.1, 10.0)
                view.viewport = Viewport(0, 0, width, height)
            }

            override fun surfaceDestroyed(holder: android.view.SurfaceHolder) {}
        })
    }

    private fun loadModel(modelFile: String) {
        val assetLoader = AssetLoader(filamentEngine, EntityManager.get())
        val resourceLoader = ResourceLoader(filamentEngine)
        val content = assets.open(modelFile).readBytes()

        val model = assetLoader.createModel(content)
        resourceLoader.loadResources(model)
        scene.addEntity(model.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        Engine.destroy(filamentEngine)
    }
}*/
