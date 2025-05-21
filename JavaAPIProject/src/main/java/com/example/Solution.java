package com.example;

public class Solution extends Species{
    private double concentration;
    private double pH;
    private double pKa;
    private double Ka;
    private double volume;
    private String acidity;
    private boolean acidbase;

    public Solution(int CID, double c, double v, boolean acidbase) {
        super(CID);
        if (acidbase) {
            pKa = Science.round((Data.valueConditions(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Dissociation+Constants")))), 2);
            Ka = Math.pow(10, -1 * pKa);
            updatePH();
        } else {
            pH = Science.round((Data.pHValue(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=pH")))), 2);
        }
        decideAcidity();
        concentration = c;
        volume = v;
        setPhase("aq");
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
        if (acidbase) {
            pH = (pKa - Math.log10(concentration)) / 2;
        }
    }

    public void updatePH(double c, double v) {
        pH = ((Math.pow(10, -1 * pH) * volume) + (c * v)) / (volume + v);
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
