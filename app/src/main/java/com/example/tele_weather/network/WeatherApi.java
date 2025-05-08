package com.example.tele_weather.network;
import com.example.tele_weather.models.Forecast;
import com.example.tele_weather.models.Location;
import com.example.tele_weather.models.Sport;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    // aqui obtendramos de cada campo los datos que escribes
    // primero la localidad, el pronostico y deportes
    @GET("search.json")
    Call<List<Location>> searchLocations(
            @Query("key") String apiKey,
            @Query("q") String location
    );

    @GET("forecast.json")
    Call<Forecast> getForecast(
            @Query("key") String apiKey,
            @Query("q") String idLocation,
            @Query("days") int days
    );

    @GET("sports.json")
    Call<Sport> getSports(
            @Query("key") String apiKey,
            @Query("q") String location
    );
}