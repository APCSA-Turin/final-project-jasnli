package com.example;

public class Science {
    public Science() {}

    public static double toCelsius(double temp) {
        temp -= 32;
        temp *= (5.0/9.0);
        return temp;
    }

    public static int round(double num) {
        return (int) Math.round(num + 0.5);
    }

    public static double round(double num, int place) {
        return ((int) (num * Math.pow(10, place))) / 100.0;
    }

    public static String getDegreeUnit(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.substring(i, i+1).equals("°") && str.substring(i+1, i+2).equals("C")) {
                return "C";
            } 
            if (str.substring(i, i+1).equals("°") && str.substring(i+1, i+2).equals("F")) {
                return "F";
            } 
        }
        return "";
    }

    public static double toATM(double torr) {
        return torr * (1/760.0);
    }
}
