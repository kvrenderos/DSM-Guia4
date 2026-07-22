package com.example.guia4

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "Guia4-Permisos"
    private val CODIGO_SOLICITUD_GRABAR = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarPermiso()
    }

    //1. Comprobar estado del permiso
    private fun comprobarEstadoPermiso() {
        val estadoPermiso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (estadoPermiso == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permiso concedido")
        } else {
            Log.i(TAG, "Permiso denegado")
        }
    }

    //2. Configurar permiso
    private fun configurarPermiso() {

        val estadoPermiso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (estadoPermiso != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG, getString(R.string.permiso_audio_denegado))

            val mostrarRequest =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )

            if (mostrarRequest) {

                AlertDialog.Builder(this)
                    .setTitle("Permiso requerido")
                    .setMessage(getString(R.string.permisos_audio_requerido))
                    .setPositiveButton("OK") { _, _ ->
                        solicitudPermiso()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()

            } else {
                solicitudPermiso()
            }

        } else {

            Log.i(TAG, "Permiso ya concedido")

        }
    }

    //3. Solicitar permiso
    private fun solicitudPermiso() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            CODIGO_SOLICITUD_GRABAR
        )
    }

    //4. Resultado del permiso
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

                    Log.i(TAG, getString(R.string.permisos_audio_concedido_usuario))

                    Toast.makeText(
                        this,
                        getString(R.string.permisos_audio_concedido_usuario),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Log.i(TAG, getString(R.string.permiso_audio_denegado_usuario))

                    Toast.makeText(
                        this,
                        getString(R.string.permiso_audio_denegado_usuario),
                        Toast.LENGTH_SHORT
                    ).show()

                    /*Codigo cuando el usuario conseda el permiso */
                }
            }
        }
    }
}