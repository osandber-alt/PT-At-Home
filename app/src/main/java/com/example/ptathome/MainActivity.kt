package com.example.ptathome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.use
import com.google.android.filament.Engine
import com.google.android.filament.EntityManager
import com.google.android.filament.Filament
import com.google.android.filament.IndirectLight
import com.google.android.filament.Scene
import com.google.android.filament.Skybox
import com.google.android.filament.SwapChain
import com.google.android.filament.utils.KtxLoader
import com.google.android.filament.utils.ModelViewer
import com.example.ptathomepackage.FilamentView // Import your FilamentView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import kotlin.concurrent.read

class MainActivity : AppCompatActivity() {

    private lateinit var engine: Engine
    private lateinit var surfaceView: FilamentView
    private lateinit var modelViewer: ModelViewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Filament
        Filament.init()

        // Create FilamentView and set it as content view
        surfaceView = FilamentView(this)
        setContentView(surfaceView)

        // Create Filament Engine
        engine = Engine.create()

        // Create ModelViewer
        modelViewer = ModelViewer(surfaceView)

        // Set scene and render callback
        surfaceView.scene = modelViewer.scene
        surfaceView.renderCallback = object : FilamentView.RenderCallback {
            override fun onNativeWindowChanged(surface: SwapChain) {
                modelViewer.swapChain = surface
            }

            override fun onDetachedFromSurface() {
                modelViewer.swapChain = null
            }

            override fun onResized(width: Int, height: Int) {
                // Update viewport in FilamentView, not ModelViewer
                surfaceView.viewport = com.google.android.filament.Viewport(0, 0, width, height)
                surfaceView.view.viewport = surfaceView.viewport
            }
        }

        // Load the model and set up the scene
        loadGlb("DamagedHelmet.glb") // Replace with your model file
        setupScene()
    }

    private fun loadGlb(assetName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val buffer = assets.open(assetName).use { input ->
                val bytes = ByteArray(input.available())
                input.read(bytes)
                ByteBuffer.wrap(bytes)
            }
            modelViewer.loadModelGlb(buffer)
            modelViewer.transformToUnitCube()
        }
    }

    private fun setupScene() {
        val scene = modelViewer.scene
        val ibl = "default_env" // Replace with your IBL if needed
        readCompressedAsset("envs/$ibl/${ibl}_ibl.ktx").let {
            scene.indirectLight = KtxLoader.createIndirectLight(engine, it)
            scene.indirectLight!!.intensity = 30_000.0f
        }
        readCompressedAsset("envs/$ibl/${ibl}_skybox.ktx").let {
            scene.skybox = KtxLoader.createSkybox(engine, it)
        }
    }

    private fun readCompressedAsset(assetName: String): ByteBuffer {
        val input = assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        engine.destroy()
    }
}
/* Possible Webview 0.2
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/index.html")
        setContentView(webView)
    }
}*/


/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ptathome.ui.theme.PTAtHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PTAtHomeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello 12564 $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PTAtHomeTheme {
        Greeting("Android")
    }
}*/