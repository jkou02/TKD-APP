package com.jkou.tkd_app

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forzar orientación horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_main)

        // Obtener referencias a todos los botones
        val bigRectButton = findViewById<Button>(R.id.bigRectButton)
        val circleButton1 = findViewById<MaterialButton>(R.id.circleButton1)
        val circleButton2 = findViewById<MaterialButton>(R.id.circleButton2)
        val smallRectButton1 = findViewById<Button>(R.id.smallRectButton1)
        val mediumRectButton = findViewById<Button>(R.id.mediumRectButton)
        val dynamicRectButton = findViewById<Button>(R.id.dynamicRectButton)

        // Lista de todos los botones
        val buttons = listOf(
            bigRectButton, circleButton1, circleButton2,
            smallRectButton1, mediumRectButton, dynamicRectButton
        )

        // Asignar click listener a cada botón para cambiar color
        buttons.forEach { button ->
            button.setOnClickListener {
                // Alternar entre rojo y azul
                val currentColor = if (button is MaterialButton) {
                    button.backgroundTintList?.defaultColor ?: Color.RED
                } else {
                    (button as Button).backgroundTintList?.defaultColor ?: Color.RED
                }

                val newColor = if (currentColor == Color.RED) Color.BLUE else Color.RED
                button.setBackgroundColor(newColor)
            }
        }

        // Configurar movimiento aleatorio para el botón dinámico
        dynamicRectButton.post {
            val displayMetrics = resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels

            // Posición aleatoria dentro de los límites de la pantalla
            val randomX = Random.nextInt(0, screenWidth - dynamicRectButton.width)
            val randomY = Random.nextInt(0, screenHeight - dynamicRectButton.height)

            dynamicRectButton.x = randomX.toFloat()
            dynamicRectButton.y = randomY.toFloat()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Restaurar rotación normal al salir (opcional)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}