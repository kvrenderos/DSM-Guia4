package com.example.guia4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "Guia4-Permisos"
    private val CODIGO_SOLICITUD_GRABAR = 101
    private lateinit var btnIniciarGrabacion: Button
    private lateinit var btnCamara: Button
    private lateinit var txtEstadoGrabacion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIniciarGrabacion = findViewById(R.id.btnIniciarGrabacion)
        btnCamara = findViewById(R.id.btnCamara)
        txtEstadoGrabacion = findViewById(R.id.txtEstadoGrabacion)

        configurarPermiso()

        btnIniciarGrabacion.setOnClickListener {
            iniciarGrabacion()
        }

        btnCamara.setOnClickListener {
            val intent = Intent(this, CamaraActivity::class.java)
            startActivity(intent)
        }
    }

    // 1. Comprobar estado del permiso
    private fun comprobarEstadoPermiso() {
        val estadoPermiso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (estadoPermiso == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, getString(R.string.permiso_audio_concedido))
        } else {
            Log.i(TAG, getString(R.string.permiso_audio_denegado))
        }
    }

    // 2. Configurar permiso
    private fun configurarPermiso() {
        comprobarEstadoPermiso()

        val estadoPermiso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (estadoPermiso != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, getString(R.string.permiso_audio_denegado))
            txtEstadoGrabacion.text = getString(R.string.estado_esperando_permiso)
            btnIniciarGrabacion.isEnabled = false

            val mostrarRequest =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )

            if (mostrarRequest) {

                AlertDialog.Builder(this)
                    .setTitle("Permiso requerido")
                    .setMessage(getString(R.string.permiso_audio_requerido))
                    .setPositiveButton("OK") { _, _ ->
                        solicitudPermiso()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()

            } else {
                solicitudPermiso()
            }

        } else {
            Log.i(TAG, getString(R.string.permiso_audio_concedido))
            btnIniciarGrabacion.isEnabled = true
            txtEstadoGrabacion.text = getString(R.string.estado_esperando_permiso)
        }
    }

    private fun iniciarGrabacion() {
        val estadoPermiso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (estadoPermiso == PackageManager.PERMISSION_GRANTED) {
            txtEstadoGrabacion.text = getString(R.string.estado_grabando)
            Log.i(TAG, getString(R.string.estado_grabando))
        } else {
            txtEstadoGrabacion.text = getString(R.string.estado_esperando_permiso)
            solicitudPermiso()
        }
    }

    // 3. Solicitar permiso
    private fun solicitudPermiso() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            CODIGO_SOLICITUD_GRABAR
        )
    }

    // 4. Resultado del permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        when (requestCode) {

            CODIGO_SOLICITUD_GRABAR -> {

                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    Log.i(TAG, getString(R.string.permiso_audio_concedido_usuario))
                    txtEstadoGrabacion.text = getString(R.string.estado_esperando_permiso)
                    btnIniciarGrabacion.isEnabled = true

                    Toast.makeText(
                        this,
                        getString(R.string.permiso_audio_concedido_usuario),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Log.i(TAG, getString(R.string.permiso_audio_denegado_usuario))
                    txtEstadoGrabacion.text = getString(R.string.estado_permiso_denegado)
                    btnIniciarGrabacion.isEnabled = false

                    Toast.makeText(
                        this,
                        getString(R.string.permiso_audio_denegado_usuario),
                        Toast.LENGTH_SHORT
                    ).show()

                    /* Codigo cuando el usuario deniegue el permiso */
                }
            }
        }
    }
}
