package com.example;
import org.json.JSONObject;
import org.json.JSONArray;

public class Data {
    public Data() {}

    public static String extractName(JSONObject obj) {
        return (String) ((JSONObject) (obj.get("Record"))).get("RecordTitle");
    }

    // extraction of property as a string
    public static String extract(JSONObject obj) {
        while (!(obj instanceof JSONObject && ((JSONObject) obj).has("String"))) {
            JSONArray o2 = new JSONArray();
            if (obj instanceof JSONObject) {
                if (((JSONObject) obj).has("Record")) {
                    obj = (JSONObject) ( (obj.get("Record")));
                    o2 = new JSONArray();
                } if (((JSONObject) obj).has("Reference")) {
                    o2 = (JSONArray) obj.get("Reference");
                } if (((JSONObject) obj).has("Section")) {
                    o2 = (JSONArray) obj.get("Section");
                } if (((JSONObject) obj).has("Information")) {
                    o2 = (JSONArray) obj.get("Information");
                } if (((JSONObject) obj).has("Value")) {
                    obj = (JSONObject) obj.get("Value");
                    o2 = new JSONArray();
                } if (((JSONObject) obj).has("StringWithMarkup")) {
                    o2 = (JSONArray) obj.get("StringWithMarkup");
                }
            }
            if (o2.length() != 0) {
                obj = (JSONObject) o2.get(0);
            }
        }        
        String str = "";
        str = (String) ((JSONObject) obj).get("String");
        return str;

    }

    public static JSONObject JSONify(String link) {
        try {
            String data = API.getData(link);
            JSONObject obj = new JSONObject(data);
            return obj;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static double pHValue(String str) {
            String rtn = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                    rtn += str.substring(i, i+1);
                }
                if (str.substring(i, i+1).equals("f")) {
                    return Double.parseDouble(rtn);
                }
            }
            return Double.parseDouble(rtn);
    }

    public static double valueConditions(String str) {
        String rtn = "";
        for (int i = 0; i < str.length(); i++) {
            if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                rtn += str.substring(i, i+1);
            }
            else {
                return Double.parseDouble(rtn);
            }
        }
        return Double.parseDouble(rtn);
    }

    // returns the str with only double numbers
    public static double value(String str) {
        String rtn = "";
        for (int i = 0; i < str.length(); i++) {
            if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                rtn += str.substring(i, i+1);
            }
            if (str.substring(i, i+1).equals("F")) {
                return Double.parseDouble(rtn);
            }
        }
        return Double.parseDouble(rtn);
    }

    // returns the str with only int numbers
    public static int number(String str) {
        String rtn = "";
        for (int i = 0; i < str.length(); i++) {
            if (isNumeric(str.substring(i, i + 1))) {
                rtn += str.substring(i, i+1);
            }
        }
        return Integer.parseInt(rtn);
    }

    // method to check if a string is a number, if not return false
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
}
}