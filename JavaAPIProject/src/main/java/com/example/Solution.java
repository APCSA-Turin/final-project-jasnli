package com.example;

import java.io.IOException;

public class Solution extends Species{
    private double concentration;
    private double pH;
    private double pKa;
    private double Ka;
    private double originalpH;
    private double volume;
    private String acidity;

    public Solution(int CID, double c, double v) {
        super(CID);
        boolean success = false;
        
        // initialize
        pH = -1;
        pKa = -1;
        Ka = -1;
        originalpH = -1;
        volume = v;
        concentration = c;
        setPhase("aq");
        decideAcidity(); // DO LATER

        while (!success) {
            try {
                Thread.sleep(1000);

                // generate base URL to reduce redundancy
                String baseURL = "https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?";

                // extraction for pKa & Ka
                if (pKa == -1) {
                    if (Data.checkURLResponse("heading=Dissociation+Constants", baseURL)) {
                        pKa = Science.round(Data.valueSemi(Data.extract(Data.JSONify(baseURL + "heading=Dissociation+Constants"))), 2);
                        Ka = Math.pow(10, -1 * pKa);
                        updatePH();
                    }
                }
                
                // extraction for pH
                if (pH == -1) {
                    if (Data.checkURLResponse("heading=pH", baseURL)) {
                        pH = Science.round(Data.pHValue(Data.extract(Data.JSONify(baseURL + "heading=pH"))), 2);
                    }
                }
                
                success = true;
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("503")) {
                    System.out.println("Server returned 503, retrying");
                    // Retries the Program if server fails, due to high volume 
                } else {
                    System.out.println("error");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        originalpH = pH;
        decideAcidity();
    }

    // print method
    public String toString() {
        String str = super.toString();
        str += "-------------------- \n";
        str += "Solution Properties \n";
        str += "Status: " + acidity + "\n";
        str += "pH Level: " + pH + "\n";
        str += "pKa: " + pKa + "\n";
        str += "Solution Concentration: " + concentration + " \n";
        str += "Volume: " + volume + "\n";
        return str;
    }

    public double getConcentration() {return concentration;}
    public double getPH() {return pH;}
    public double getpKa() {return pKa;}
    public double getVolume() {return volume;}
    public double getOriginalPH() {return originalpH;}
    public String getAcidity() {return acidity;}

    public void decideAcidity() {
        if (pH > 7) {
            acidity = "basic";
        } if (pH < 7) {
            acidity = "acidic";
        } else {
            acidity = "neutral";
        }
    }

    public void updatePH() {
        pH = (pKa + (-1 * Math.log10(concentration))) / 2;
    }

    public void updatePH(double c, double v) {
        pH = -1 * Math.log10(((Math.pow(10, -1 * pH) * volume) + (c * v)) / (volume + v));
    }

    public void dilute(double addedV) {
        concentration = (concentration * volume) / (volume + addedV);
        volume += addedV;
    }

    // changes phase from liquid to aqeuous for a solution
    @Override
    public void setPhase(double currentTemp) {
        if (currentTemp < getBoilingPoint() && currentTemp > getMeltingPoint()) {
            setPhase("aq");
        } else {
            super.setPhase(currentTemp);
        }
    }

}
