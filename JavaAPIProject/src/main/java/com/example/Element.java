package com.example;

public class Element extends Species {
    private int protons;
    private int neutrons;
    private int electrons;
    private int charge;
    

    public Element(int CID) {
        super(CID);
        protons = Data.number(Data.extract(Data.JSONify("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + CID + "/JSON?heading=Atomic+Number")));
        electrons = protons;
        neutrons = Science.round(super.getMolecularWeight()) - protons;
        charge = 0;
    }

    public String toString() {
        String str = super.toString();
        str += "-------------------- \n";
        str += "Protons: " + protons +"\n";
        str += "Neutrons: " + neutrons +"\n";
        str += "Electrons: " + electrons +"\n";
        str += "Charge: " + charge;
        return str;
    }
}
