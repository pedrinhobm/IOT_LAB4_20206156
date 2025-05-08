package com.example.tele_weather.models;

import java.util.List;

public class Forecast {
    private ForecastContainer forecast;

    public ForecastContainer getForecast(){
        return forecast;
    }

    public void setForecast(ForecastContainer forecast){
        this.forecast = forecast;
    }
    // aqui si colocamos el contenido de un dia de un pronostico
    // cuyos datos particulares están en el ForecastDay
    // y estos serán reagrupados en una lista
    public static class ForecastContainer{
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday(){
            return forecastday;
        }

        public void setForecastday(List<ForecastDay> forecastday) {
            this.forecastday = forecastday;
        }
    }
}
