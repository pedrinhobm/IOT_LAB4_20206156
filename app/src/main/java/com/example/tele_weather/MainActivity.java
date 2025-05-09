package com.example.tele_weather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button ingresarButton;
    private TextView elaboradoPorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Desde un inicio se realizara la verificación de conexion de internet
        // ya está colocado de frente las funciones
        if (!verificarConexion()) {
            mostrarDialogoConfiguracion();
        }

        // Este es el botón de ingresar el cual fue implementado
        // con el intent para cambiar de vista
        ingresarButton = findViewById(R.id.ingresar_button);
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Aqui encontramos el texto que indica por mi elaboración
        elaboradoPorText = findViewById(R.id.elaborado_por_text);
        elaboradoPorText.setText("Elaborado por: Pedro Miguel Bustamante Melo 20206156");
    }
    // para verificar conexion , vuelvo a usar la funcion que realicé en el laboratorio anterior
    // se determina si cuenta con servicio del sistema
    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    // aqui si su ia porque no queria que se cruce con la vista xml de la vista principal
    // solo que me lanza la advertencia si es que desea la comunicación
    // aparte lo use porque no sabia como dirigir de mi aplicacion a la configuracion de red del emulador
    // o de mi propio celular
    private void mostrarDialogoConfiguracion() {
        new AlertDialog.Builder(this)
                .setTitle("Sin Conexión")
                .setMessage("Se requiere conexión a Internet. ¿Desea ir a la configuración?")
                .setPositiveButton("Configuración", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}