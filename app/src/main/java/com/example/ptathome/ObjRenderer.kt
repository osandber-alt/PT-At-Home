package com.example.ptathome

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class ObjRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private var vertexBuffer: FloatBuffer? = null
    private var vertexCount = 0
    var angleX = 0f
    var angleY = 0f

    init {
        loadObjFile("Modell.obj")
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        // Sätt bakgrundsfärg
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        // Aktivera djuptest för 3D-objekt
        gl.glEnable(GL10.GL_DEPTH_TEST)

        gl.glEnable(GL10.GL_CULL_FACE) // Aktivera culling
        gl.glCullFace(GL10.GL_BACK)    // Rita bara framåtvända ytor
        gl.glDisable(GL10.GL_LIGHTING) // Tillfälligt inaktivera belysning
        gl.glColor4f(0.8f, 0.8f, 0.8f, 1.0f) // Grå färg


        // Aktivera belysning
        gl.glEnable(GL10.GL_LIGHTING)
        gl.glEnable(GL10.GL_LIGHT0)

        // Definiera ljuskälla
        val lightPosition = floatArrayOf(0.0f, 0.0f, -1.0f, 1.0f)
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0)
        gl.glEnable(GL10.GL_COLOR_MATERIAL) // Gör så att materialet påverkas av färgen

        // Definiera material (justera för att se ljus)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, floatArrayOf(0.2f, 0.2f, 0.2f, 1.0f), 0)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, floatArrayOf(0.8f, 0.8f, 0.8f, 1.0f), 0)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        val ratio = width.toFloat() / height.toFloat()
        gl.glViewport(0, 0, width, height)

        // Perspektivprojektion för 3D-effekt
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadIdentity()
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 0.5f, 50f)  // Perspektiv, objektet kommer vara synligt från 3 till 7 enheter bort
        gl.glMatrixMode(GL10.GL_MODELVIEW)
    }

    override fun onDrawFrame(gl: GL10) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl.glLoadIdentity()

        // Flytta tillbaka objektet för att kunna se det
        gl.glTranslatef(0f, -8f, -10f)

        // Rotera objektet baserat på användarens fingerdragning
        gl.glRotatef(angleX, 1f, 0f, 0f)  // Rotation runt X-axeln (upp/ned)
        gl.glRotatef(angleY, 0f, 1f, 0f)  // Rotation runt Y-axeln (vänster/höger)

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f)  // Red color
        // Aktivera vertex array och rendera objektet
        if (vertexBuffer != null) {
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexCount)
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        }
    }

    private fun loadSimpleObject() {
        // En enkel triangel (x, y, z)
        val vertices = floatArrayOf(
            0.0f, 1.0f, 0.0f,  // Topp av triangeln
            -1.0f, -1.0f, 0.0f, // Nedre vänster
            1.0f, -1.0f, 0.0f   // Nedre höger
        )

        val byteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        vertexBuffer = byteBuffer.asFloatBuffer().apply {
            put(vertices)
            position(0)
        }

        vertexCount = vertices.size / 3
    }

    // Den här funktionen kommer att uppdatera rotation baserat på touch-input
    fun setRotation(angleX: Float, angleY: Float) {
        this.angleX = angleX
        this.angleY = angleY
    }

    fun loadObjFile(fileName: String) {
        val inputStream = context.assets.open(fileName)
        val vertices = mutableListOf<Float>()
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                if (line.startsWith("v ")) {
                    // Extract vertex data
                    val parts = line.split(" ")
                    vertices.add(parts[1].toFloat()) // x
                    vertices.add(parts[2].toFloat()) // y
                    vertices.add(parts[3].toFloat()) // z
                }
            }
        }
        val vertexArray = vertices.toFloatArray()
        val byteBuffer = ByteBuffer.allocateDirect(vertexArray.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        vertexBuffer = byteBuffer.asFloatBuffer().apply {
            put(vertexArray)
            position(0)
        }
        vertexCount = vertexArray.size / 3
    }
}








/*   private var vertexBuffer: FloatBuffer? = null
   private var vertexCount = 0

   init {
       // Load a simple object, or load a more complex OBJ file here
       loadSimpleObject()
   }

   override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {

       // Set background color to black and enable depth testing
       gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
       gl.glEnable(GL10.GL_DEPTH_TEST)

       // Enable lighting
       gl.glEnable(GL10.GL_LIGHTING)
       gl.glEnable(GL10.GL_LIGHT0)

       // Define light source position
       val lightPosition = floatArrayOf(0.0f, 0.0f, -1.0f, 1.0f)
       gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0)
   }

   override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
       val ratio = width.toFloat() / height.toFloat()
       gl.glViewport(0, 0, width, height)
       gl.glOrthof(-ratio, ratio, -1f, 1f, 1f, 10f)  // Orthographic projection
       gl.glLoadIdentity()
       gl.glTranslatef(0f, 0f, -3f)  // Move object back from the camera
   }

   override fun onDrawFrame(gl: GL10) {
       gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
      /* gl.glMatrixMode(GL10.GL_MODELVIEW)
       gl.glLoadIdentity()

       // Simple rotation
       gl.glRotatef(30f, 1f, 1f, 0f)

       if (vertexBuffer != null) {
           gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
           gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
           gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexCount)
           gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
       }*/
   }

   // Load a simple triangle
   private fun loadSimpleObject() {
       val vertices = floatArrayOf(
           0.0f, 1.0f, 0.0f,  // Top of triangle
           -1.0f, -1.0f, 0.0f, // Bottom left
           1.0f, -1.0f, 0.0f   // Bottom right
       )

       val byteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
       byteBuffer.order(ByteOrder.nativeOrder())
       vertexBuffer = byteBuffer.asFloatBuffer().apply {
           put(vertices)
           position(0)
       }
       vertexCount = vertices.size / 3
   }

   fun loadObjFile(fileName: String) {
       val inputStream = context.assets.open(fileName)
       val vertices = mutableListOf<Float>()
       inputStream.bufferedReader().useLines { lines ->
           lines.forEach { line ->
               if (line.startsWith("v ")) {
                   // Extract vertex data
                   val parts = line.split(" ")
                   vertices.add(parts[1].toFloat()) // x
                   vertices.add(parts[2].toFloat()) // y
                   vertices.add(parts[3].toFloat()) // z
               }
           }
       }
       val vertexArray = vertices.toFloatArray()
       val byteBuffer = ByteBuffer.allocateDirect(vertexArray.size * 4)
       byteBuffer.order(ByteOrder.nativeOrder())
       vertexBuffer = byteBuffer.asFloatBuffer().apply {
           put(vertexArray)
           position(0)
       }
       vertexCount = vertexArray.size / 3
   }*/
