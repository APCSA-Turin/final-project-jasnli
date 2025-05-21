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
        int index = str.indexOf("C");
        if (index >= -1) {
            return "C";
        } else {
            index = str.indexOf("F");
            if (index >= -1) {
                return "F";
            }
        }
        return "";
    }
}
