package com.example;

public class Environment {
    private double temperature;
    private double atmosphere;


    public Environment() {
        temperature = 25.00;
        atmosphere = 1.00;
    }

    public void deltaT(double temp) {
        temperature += temp;
    }

    public void deltaP(double pressure) {
        atmosphere += pressure;
    }


    public String toString() {
        String str = "";
        str+= "Current Temperature: " + temperature;
        str+= "Current Atmospheric Pressure:" + atmosphere;
        return str;
    }
}
