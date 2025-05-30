package com.example.tele_weather.fragments;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.adapters.PronosticoAdapter;
import com.example.tele_weather.models.Forecast;
import com.example.tele_weather.models.ForecastDay;
import com.example.tele_weather.network.WeatherApi;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PronosticoFragment extends Fragment implements SensorEventListener {

    private EditText idLocationEditText;
    private EditText daysForecastEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private PronosticoAdapter pronosticoAdapter;
    private List<ForecastDay> forecastDaysList = new ArrayList<>();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float umbral_aceleracion = 20.0f;
    private WeatherApi weatherApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pronostico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idLocationEditText = view.findViewById(R.id.id_location_edit_text);
        daysForecastEditText = view.findViewById(R.id.days_forecast_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.forecast_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pronosticoAdapter = new PronosticoAdapter(forecastDaysList); // Use forecastDaysList
        recyclerView.setAdapter(pronosticoAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);

        if (getArguments() != null) {
            String idLocation = getArguments().getString("idLocation");
            if (idLocation != null) {
                idLocationEditText.setText(idLocation);
                buscarPronosticos(idLocation, 14);
            }
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // en esta funcion usa ia debido a que no me mostraba un error si ...
                // si primmero no completaba ambos campos y segundo por si no validaba un numero de 1 al 14 dias
                String idLocation = idLocationEditText.getText().toString().trim();
                String daysText = daysForecastEditText.getText().toString().trim();
                // ese fue un problema por el cual si no lo asignaba , me botaba de la aplicacion
                // es por eso que le agregue estos casos que he mencionado
                if (idLocation.isEmpty() || daysText.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete ambos campos.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int days;
                try {
                    days = Integer.parseInt(daysText);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Ingrese un número válido para los días.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (days < 1 || days > 14) { // por esta selectiva ha sido la primordial
                    Toast.makeText(getContext(), "El número de días debe estar entre 1 y 14.", Toast.LENGTH_SHORT).show();
                    return;
                }
                buscarPronosticos(idLocation, days);
            }
        });


        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void buscarPronosticos(String idLocation, int days) {
        Call<Forecast> call = weatherApi.getForecast("ec24b1c6dd8a4d528c1205500250305", "id:" + idLocation, days);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful() && response.body() != null) {
                    forecastDaysList.clear();
                    Forecast forecastResponse = response.body();
                    if (forecastResponse != null && forecastResponse.getForecast() != null) {
                        forecastDaysList.addAll(forecastResponse.getForecast().getForecastday());
                        pronosticoAdapter.notifyDataSetChanged();
                    } else {
                        mostrarError("No se encontraron datos");
                    }
                } else {
                    mostrarError("Pronostico no encontrado");
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                mostrarError("Error de conexión.");
            }
        });
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    // en el caso de sensor si use ia ya que hasta el momento
    // no entendia o sabia que funcion puedeb funcionar un sensor mientras agitas el celular
    // es por ello que tuve que comprobarlo con mi movil
    @Override
    public void onSensorChanged(SensorEvent event) { // de este sensor se va recopilar 3 valores
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0]; // y de aceurdo a ello, calculamos su modulo o magnitud
            float y = event.values[1]; // para obtener la aceleracion
            float z = event.values[2]; // pero lo convertimos en variable float
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);
            if (acceleration > umbral_aceleracion) { // y con una selectiva medimos si llega a superarlo o no a 20 ms2
                mostrarDialogoConfirmacion(); // si es del caso nos dirigmos a la pregunta de confirmacion
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void mostrarDialogoConfirmacion() { // en dicho dialogo volvemos usar un AlertDialog
        new AlertDialog.Builder(requireContext()) // para confirmar si deseas eleminar los pronositcos
                .setTitle("Confirmar Acción") // y luego limpiarlos
                .setMessage("¿Desea eliminar los últimos pronósticos?") // esto si lo use con la funcion que obtuve
                .setPositiveButton("Sí", (dialog, which) -> { // desde el MainActivity cuando no habia conexion de internet
                    forecastDaysList.clear();
                    pronosticoAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}