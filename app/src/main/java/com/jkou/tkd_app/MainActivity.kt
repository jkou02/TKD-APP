package com.jkou.tkd_app

import com.jkou.tkd_app.model.Event
import com.jkou.tkd_app.model.EventResponse
import com.jkou.tkd_app.network.RetrofitClient
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Forzar orientación horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.topToolbar)
        setSupportActionBar(toolbar)

        // Botones del peleador rojo
        findViewById<MaterialButton>(R.id.btnPtdHeadRed).setOnClickListener {
            showToast("Patada a la cabeza - ROJO")
            enviarEvento("kick_head", "rojo")
        }

        findViewById<MaterialButton>(R.id.btnPtdPetoRed).setOnClickListener {
            showToast("Patada al peto - ROJO")
            enviarEvento("kick_chest", "rojo")
        }

        findViewById<MaterialButton>(R.id.btnGpPetoRed).setOnClickListener {
            showToast("Golpe al peto - ROJO")
            enviarEvento("punch", "rojo")
        }

        findViewById<MaterialButton>(R.id.btnGiroRed).setOnClickListener {
            showToast("Giro - ROJO")
            enviarEvento("spin_kick", "rojo")
        }

        // Botones del peleador azul
        findViewById<MaterialButton>(R.id.btnPtdHeadBlue).setOnClickListener {
            showToast("Patada a la cabeza - AZUL")
            enviarEvento("kick_head", "azul")
        }

        findViewById<MaterialButton>(R.id.btnPtdPetoBlue).setOnClickListener {
            showToast("Patada al peto - AZUL")
            enviarEvento("kick_chest", "azul")
        }

        findViewById<MaterialButton>(R.id.btnGpPetoBlue).setOnClickListener {
            showToast("Golpe al peto - AZUL")
            enviarEvento("punch", "azul")
        }

        findViewById<MaterialButton>(R.id.btnGiroBlue).setOnClickListener {
            showToast("Giro - AZUL")
            enviarEvento("spin_kick", "azul")
        }

        // Botones funcionales adicionales
        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            showToast("Conectar con el juez")
        }

        findViewById<Button>(R.id.btnTestSenal).setOnClickListener {
            showToast("Testear la señal")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarEvento(eventType: String, judgeId: String = "juez_1", matchId: String = "match_001") {
        val timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        val event = Event(
            judge_id = judgeId,
            match_id = matchId,
            event_type = eventType,
            timestamp = timestamp
        )

        RetrofitClient.api.enviarEvento(event).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Toast.makeText(this@MainActivity, "✔ ${body?.message}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "❌ Error ${response.code()}: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "⚠ Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                Toast.makeText(this, "Abrir Configuración", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_exit -> {
                finishAffinity()  // Cierra la app completamente
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Restaurar rotación normal al salir (opcional)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}