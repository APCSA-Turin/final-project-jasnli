package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

public class Species {
    // FORMAT https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/(CID)/JSON?heading=(PROPERTY)
    private int CID;
    private String name;
    private double molecularWeight;
    private double BP;
    private double MP;
    private String phase; // "s" = solid, "l" = liquid, "g" = gas, "aq" = aqueous
    private double heatOfCombustion;
    private double density;
    private double vaporPressure;
    private boolean combustable;


    public Species(int CID) {
        this.CID = CID;
        boolean success = false;
        int delay = 1000;
        int attemptNumber = 0;

        // initialization to blanks!
        name = "";
        molecularWeight = -1;
        BP = Integer.MAX_VALUE;
        MP = Integer.MAX_VALUE;
        heatOfCombustion = Integer.MAX_VALUE;
        density = -1;
        vaporPressure = -1;
        combustable = false;
        
        // loop for data
        while(!success) {
            try {
                // in order to slow down retries
                attemptNumber ++;
                Thread.sleep((delay * attemptNumber));
                if (attemptNumber > 2) {
                    attemptNumber = 2;
                }

                // base URL to make it more concise
                String baseURL = "https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?";

                // extraction for name
                if (name.equals("")) {
                    name = Data.extractName(Data.JSONify(baseURL + "heading=Synonyms"));
                }

                // extraction for molecularWeight
                if (molecularWeight == -1) {
                    molecularWeight = Data.value(Data.extract(Data.JSONify(baseURL + "heading=Molecular+Weight")));
                }

                // extraction for boiling point
                if (BP == Integer.MAX_VALUE) {
                    String strBP = Data.extract(Data.JSONify(baseURL + "heading=Boiling+Point"));

                    // check for units
                    if (Science.getDegreeUnit(strBP).equals("C")) {
                        BP = Science.round(Data.valueConditions(strBP), 2);
                    } else {
                        BP = Science.round(Science.toCelsius(Data.valueConditions(strBP)), 2);
                    }
                }

                // extraction for melting point
                if (MP == Integer.MAX_VALUE) {
                    String strMP = Data.extract(Data.JSONify(baseURL + "heading=Melting+Point"));

                    // check for units
                    if (Science.getDegreeUnit(strMP).equals("C")) {
                        MP = Science.round(Data.valueConditions(strMP), 2);
                    } else {
                        MP = Science.round(Science.toCelsius(Data.valueConditions(strMP)), 2);
                    }
                }

                // extraction for combustion
                boolean containsHeat = Data.checkURLResponse("heading=Heat+of+Combustion", baseURL);

                if (containsHeat) {
                    combustable = true;
                    if (heatOfCombustion == Integer.MAX_VALUE) {
                        heatOfCombustion = Math.abs(Science.round(Data.valueConditions(Data.extract(Data.JSONify(baseURL + "heading=Heat+of+Combustion"))), 2));
                    } else {
                        heatOfCombustion = 0;
                    }
                } else {
                    heatOfCombustion = 0;
                }

                // extraction for density
                if (density == -1) {
                    density = Science.round(Data.valueConditions(Data.extract(Data.JSONify(baseURL + "heading=Density"))), 2);
                }

                // extraction for vapor pressure
                if (vaporPressure == -1) {
                    vaporPressure = Science.round(Science.toATM(Data.valueConditions(Data.extract(Data.JSONify(baseURL + "heading=Vapor+Pressure")))));
                }

                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            setPhase(25);
        }
    }


    // detailed information
    public String toString() {
        String str = "";
        str += "Species Name: " + name + "\n";
        str += "PUBChem CID: " + CID + "\n";
        str += "Molecular Weight " + molecularWeight + "\n";
        str += "-------------------- \n";
        str += "Physical Properties\n";
        str += "Phase: " + phase + "\n";
        str += "Boiling Point: " + BP + " °C\n";
        str += "Melting Point: " + MP + " °C\n";
        str += "Density: " + density + " g/L\n";
        str += "Vapor Pressure: " + vaporPressure + " atm\n";
        if (combustable) {
            str += "Heat of Combustion: " + heatOfCombustion + " kJ/mol\n";
        } else {
            str += "Heat of Combustion: N/A\n";
        }
        
        return str;
    }

    public boolean combustable() {return combustable;}
    public double getDensity() {return density;}
    public double getVP() {return vaporPressure;}
    public String getPhase() {return phase;}
    public int getCID() {return CID;}
    public String getName() {return name;}
    public double getMolecularWeight() {return molecularWeight;}
    public double getBoilingPoint() {return BP;}
    public double getMeltingPoint() {return MP;}
    public double getHeatofCombustion() {return heatOfCombustion;}

    // setters
    public void setPhase(String s) {phase = s;}

    // phase setting
    public void setPhase(double currentTemp) {
        if (currentTemp < MP) {
            phase = "s";
        } if (currentTemp > MP && currentTemp < BP) {
            phase = "l";
        } if (currentTemp > BP) {
            phase = "g";
        } if (currentTemp == MP) {
            phase = "s/l";
        } if (currentTemp == BP) {
            phase = "l/g";
        }
    }

}
