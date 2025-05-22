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
    private String acidbase;

    public Solution(int CID, double c, double v, String acidbase) throws IOException {
        super(CID, false);
        boolean success = false;
        this.acidbase = acidbase;
        while (!success) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (acidbase.equals("weakAcid")) {
                pKa = Science.round((Data.valueSemi(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Dissociation+Constants")))), 2);
                Ka = Math.pow(10, -1 * pKa);
            } else if (acidbase.equals("weakBase")) {
                pKa = 14.00 - Science.round((Data.valueSemi(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Dissociation+Constants")))), 2);
                Ka = Math.pow(10, -1 * pKa);
            } else {
                pKa = 1.00;
            }
            //pH = Science.round((Data.pHValue(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=pH")))), 2);
            
            concentration = c;
            volume = v;
            setPhase("aq");
            updatePH();
            originalpH = pH;
            decideAcidity();
            success = true;
        }
        
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
