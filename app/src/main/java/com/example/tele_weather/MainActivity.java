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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private Button ingresarButton;
    private TextView elaboradoPorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Validar conexión a Internet
        if (!verificarConexion()) {
            mostrarDialogoConfiguracion();
        }

        // Botón "Ingresar"
        ingresarButton = findViewById(R.id.ingresar_button);
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Texto "Elaborado por..."
        elaboradoPorText = findViewById(R.id.elaborado_por_text);
        elaboradoPorText.setText("Elaborado por: Tu Nombre / Tu Código");
    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

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