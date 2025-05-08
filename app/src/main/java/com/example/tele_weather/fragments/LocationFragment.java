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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.adapters.LocationAdapter;
import com.example.tele_weather.models.Location;
import com.example.tele_weather.network.WeatherApi;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationFragment extends Fragment {

    private EditText searchLocationEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private List<Location> locationList = new ArrayList<>();
    private NavController navController;
    private WeatherApi weatherApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        searchLocationEditText = view.findViewById(R.id.location_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.location_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locationAdapter = new LocationAdapter(locationList, navController);
        recyclerView.setAdapter(locationAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchLocationEditText.getText().toString();
                buscarLocaciones(query);
            }
        });
    }

    private void buscarLocaciones(String query) {
        Call<List<Location>> call = weatherApi.searchLocations("ec24b1c6dd8a4d528c1205500250305", query);
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locationList.clear();
                    locationList.addAll(response.body());
                    locationAdapter.notifyDataSetChanged();
                } else {
                    mostrarError("No se encontraron datos");
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                mostrarError("Error de red");
            }
        });
    }
    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}