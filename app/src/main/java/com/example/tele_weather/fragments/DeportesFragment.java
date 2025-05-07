package com.example.tele_weather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.adapters.DeportesAdapter;
import com.example.tele_weather.models.Sport;  // Si creas una clase Sport
import com.example.tele_weather.network.WeatherApi;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeportesFragment extends Fragment {

    private EditText locationEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private DeportesAdapter deportesAdapter;
    private List<Sport> sportList = new ArrayList<>();  // O usa un tipo de lista adecuado
    private WeatherApi weatherApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deportes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationEditText = view.findViewById(R.id.location_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.deportes_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deportesAdapter = new DeportesAdapter(sportList);
        recyclerView.setAdapter(deportesAdapter);

        // Inicializar Retrofit (Opcional)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationEditText.getText().toString();
                buscarDeportes(location);
            }
        });
    }

    private void buscarDeportes(String location) {
        // Usa Retrofit para hacer la llamada a la API (Ejemplo)
        Call<Sport> call = weatherApi.getSports("ec24b1c6dd8a4d528c1205500250305", location);
        call.enqueue(new Callback<Sport>() {
            @Override
            public void onResponse(Call<Sport> call, Response<Sport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sportList.clear();
                    sportList.add(response.body());  //  Asume que recibes un objeto Sport
                    deportesAdapter.notifyDataSetChanged();
                } else {
                    mostrarError("No se encontraron datos deportivos en ese local");
                }
            }

            @Override
            public void onFailure(Call<Sport> call, Throwable t) {
                mostrarError("Error de red o conexión");
            }
        });
    }
    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

}