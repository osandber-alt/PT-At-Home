package com.example.ptathome.Renders

import android.content.Context
import org.rajawali3d.loader.LoaderOBJ
import org.rajawali3d.Object3D
import org.rajawali3d.renderer.RajawaliRenderer
import org.rajawali3d.loader.LoaderOBJ
import org.rajawali3d.math.vector.Vector3

class MyRenderer(context: Context) : RajawaliRenderer(context) {

    lateinit var objModel: Object3D

    override fun initScene() {
        super.initScene()
        // Load .obj model
        val loader = LoaderOBJ(resources, textureManager, R.raw.my_model)  // Replace with your file
        objModel = loader.parsedObject
        objModel.scale = Vector3(0.5f, 0.5f, 0.5f)
        currentScene.addChild(objModel)
    }

    override fun onRenderFrame() {
        super.onRenderFrame()
        // Optionally rotate the model
        objModel.rotate(Vector3(0f, 1f, 0f), 1f)
    }
}
