package com.example.ptathome


import android.view.Surface
import com.example.ptathomepackage.FilamentView
import com.google.android.filament.Engine
import com.google.android.filament.EntityManager
import com.google.android.filament.IndirectLight
import com.google.android.filament.Scene
import com.google.android.filament.Skybox
import com.google.android.filament.SwapChain
import com.google.android.filament.TransformManager
import com.google.android.filament.View
import com.google.android.filament.Viewport
import java.nio.Buffer
import kotlin.collections.toFloatArray
import com.google.android.filament.gltfio.AssetLoader
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.gltfio.MaterialProvider
import com.google.android.filament.gltfio.UbershaderLoader
import com.google.android.filament.gltfio.ResourceLoader

class ModelViewer(val filamentView: FilamentView) {

    var engine: Engine = filamentView.engine
    var scene: Scene? = null
    var swapChain: SwapChain? = null
    var view: View = filamentView.view
    var viewport: Viewport = filamentView.viewport
    private lateinit var assetLoader: AssetLoader
    private var filamentAsset: FilamentAsset? = null

    init {
        scene = engine.createScene()
        // Initialize AssetLoader after Filament initialization in onSurfaceCreated callback
        filamentView.renderCallback = object : FilamentView.RenderCallback {
            override fun onSurfaceCreated() {
                // Create a MaterialProvider using UbershaderLoader
                val materialProvider = UbershaderLoader.create(engine)
                // Get the EntityManager instance
                val entityManager = EntityManager.get()
                assetLoader = AssetLoader(engine, materialProvider, entityManager)
            }

            // ... other callback methods ...
        }
    }

    fun loadModelGlb(buffer: Buffer) {
        filamentAsset = assetLoader.createAsset(buffer)
        filamentAsset?.let { asset ->
            scene?.addEntities(asset.entities)
        }
    }

    fun transformToUnitCube() {
        filamentAsset?.let { asset ->
            val tm = engine.transformManager
            val inst = asset.root
            val tcm = tm.getInstance(inst)
            val transform = FloatArray(16)
            asset.getTransform(inst, transform)
            val center = asset.boundingBox.center.toFloatArray()
            val halfExtent = asset.boundingBox.halfExtent.toFloatArray()
            val maxExtent = 2 * halfExtent[halfExtent.indexOfMax()]
            val scaleFactor = 2f / maxExtent
            tm.setTransform(tcm,
                floatArrayOf(
                    scaleFactor, 0f, 0f, 0f,
                    0f, scaleFactor, 0f, 0f,
                    0f, 0f, scaleFactor, 0f,
                    -center[0] * scaleFactor, -center[1] * scaleFactor, -center[2] * scaleFactor, 1f
                )
            )
        }
    }

    fun destroyModel() {
        filamentAsset?.let {
            assetLoader.destroyAsset(it)
            filamentAsset = null
        }
    }

    fun destroy() {
        engine.destroyScene(scene!!)
        destroyModel()
        assetLoader.destroy()
    }
}