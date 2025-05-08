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
import com.example.tele_weather.models.Sport;
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
    private List<Sport> sportList = new ArrayList<>();
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
        Call<Sport> call = weatherApi.getSports("ec24b1c6dd8a4d528c1205500250305", location);
        call.enqueue(new Callback<Sport>() {
            @Override
            public void onResponse(Call<Sport> call, Response<Sport> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getFootball() != null && !response.body().getFootball().isEmpty()) {
                    sportList.clear(); // response.body().getFootball() != null && !response.body().getFootball().isEmpty() tuve que agregar estas condiciones
                    sportList.add(response.body()); // .. con ia debido a que si colocaba una ciudad donde habia partido , me botaba de la aplicacion
                    deportesAdapter.notifyDataSetChanged();// similar al problema del numero de dias del pronositco del clima, por eso colocamos la ficon isEmpty() en caso no lo encontraran
                } else { // y aqui esta el mensaje de error al no encontrarlo
                    mostrarError("La ciudad que busca no está disponible");
                }
            }
            @Override
            public void onFailure(Call<Sport> call, Throwable t) {
                mostrarError("Error de conexión");
            }
        });
    }
    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}