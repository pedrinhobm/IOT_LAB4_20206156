package com.example.tele_weather.models;
import java.util.List;

public class Sport {
    // aqui va suceder de forma similar que se recopilara los datos
    // de futbol en una lista
    private List<Football> football;

    public List<Football> getFootball(){
        return football;
    }
    public void setFootball(List<Football> football){
        this.football = football;
    }
}