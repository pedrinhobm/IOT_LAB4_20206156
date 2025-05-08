package com.example.tele_weather.models;

public class Location {
    private int id;
    private String name;
    private String region;
    private String country;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getRegion(){
        return region;
    }
    public void setRegion(String region){
        this.region = region;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }
}