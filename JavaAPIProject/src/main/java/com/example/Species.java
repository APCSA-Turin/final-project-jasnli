package com.example;

public class Species {
    // FORMAT https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/(CIS)/JSON?heading=(PROPERTY)
    private int CID;
    private String name;
    private double molecularWeight;
    private double BP;
    private double MP;
    private String phase; // "s" = solid, "l" = liquid, "g" = gas, "aq" = aqueous
    private int[] moveSet;


    public Species(int CID) {
        this.CID = CID;
        name = Data.extractName(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Synonyms"));
        molecularWeight = Data.value(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Molecular+Weight")));
        String sBP = (Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Boiling+Point")));
        if (Science.getDegreeUnit(sBP).equals("C")) {
            BP = Science.round(Data.valueConditions(sBP));
        } else {
            BP = Science.round(Science.toCelsius(Data.valueConditions(sBP)));
        }
        // BP = Science.round((Data.valueConditions(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Boiling+Point")))), 2);
        String sMP = (Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Melting+Point")));
        if (Science.getDegreeUnit(sMP).equals("C")) {
            MP = Science.round(Data.valueConditions(sMP));
        } else {
            MP = Science.round(Science.toCelsius(Data.valueConditions(sMP)));
        }
        // MP = Science.round((Data.value(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Melting+Point")))), 2);
        setPhase(25);
        moveSet = new int[6];
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
        return str;
    }

    public int getCID() {return CID;}
    public String getName() {return name;}
    public double getMolecularWeight() {return molecularWeight;}
    public double getBoilingPoint() {return BP;}
    public double getMeltingPoint() {return MP;}

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
