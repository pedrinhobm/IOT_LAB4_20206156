package com.example.tele_weather.models;

public class Day {
    // en el caso de un dia , el tipo de variable de double
    // de las temperaturas ya que son números con decimales
    private double maxtemp_c;
    private double mintemp_c;
    private Condition condition;

    public double getMaxtemp_c(){
        return maxtemp_c;
    }
    public void setMaxtemp_c(double maxtemp_c){
        this.maxtemp_c = maxtemp_c;
    }
    public double getMintemp_c(){
        return mintemp_c;
    }
    public void setMintemp_c(double mintemp_c){
        this.mintemp_c = mintemp_c;
    }
    public Condition getCondition(){
        return condition;
    }
    public void setCondition(Condition condition){
        this.condition = condition;
    }
}