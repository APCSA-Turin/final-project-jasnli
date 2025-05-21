package com.example;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        // BP, MP, DENSITY API : https://commonchemistry.cas.org/api-overview?utm_campaign=GLO_GEN_ANY_CCH_LDG&utm_medium=EML_CAS%20_ORG&utm_source=EMCommon%20Chemistry%20API
        // PUBCHEM API : https://pubchem.ncbi.nlm.nih.gov/docs/pug-rest#section=Output
        // FORMAT https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/(CIS)/JSON?heading=(PROPERTY)
        try {
            // String water = API.getData("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/962/JSON?heading=Vapor+Pressure");
            
            // //String JSONdata1 = API.getData("https://commonchemistry.cas.org/api/detail?cas_rn=50-36-2");
            // ////System.out.println(JSONdata2);
            // //System.out.println(JSONdata1);

            // JSONObject waterVP = new JSONObject(water);
            // // JSONObject rec = (JSONObject) (obj.get("Record"));
            // // JSONObject arr = ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) (rec.get("Section"))).get(0)).get("Section")).get(0)).get("Section")).get(0)).get("Information")).get(0)).get("Value"));
            // // String arr2 = (String) ((JSONObject) ((JSONArray) (arr.get("StringWithMarkup"))).get(0)).get("String");
            // // // JSONArray str = (JSONArray) obj.get("Section");
            // // System.out.println(arr2);
            // System.out.println(Data.extract(waterVP));
            // System.out.println(Data.value(Data.extract(waterVP)));
            
            //JSONArray pKa = obj.getJSONArray("CHANGE LATER");
            // int molecularWeight = JSONO
            //JSONObject props = new JSONObject(JSONdata);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Solution cobalt_chloride = new Solution(3032536, 1.0, 0.5, false);
        System.out.println(cobalt_chloride);

        Thread.sleep(2000);

        // Element sodium = new Element(5360545);
        // System.out.println(sodium);

        
    }
}
