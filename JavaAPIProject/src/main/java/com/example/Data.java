package com.example;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;


public class Data {
    public Data() {}

    // specific extraction for the name of a substance due to the nature of the JSON format
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

    // turn the link into a JSON object
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

    // specific extraction of a pH value due to unique formatting
    public static double pHValue(String str) {
            String rtn = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                    rtn += str.substring(i, i+1);
                }
                if (str.substring(i, i+1).equals("f") || (str.substring(i, i+1).equals("("))) {
                    return Double.parseDouble(rtn);
                }
            }
            return Double.parseDouble(rtn);
    }

    // specific extraction of a value for the FIRST number and commas
    public static double valueConditions(String str) {
        String rtn = "";
        boolean detected = false;
        for (int i = 0; i < str.length(); i++) {
            if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-") || str.substring(i, i+1).equals(",")) {
                detected = true;
                if (!str.substring(i, i+1).equals(",")) {
                    rtn += str.substring(i, i+1);
                }
            }
            else if (detected) {
                return Double.parseDouble(rtn);
            }
        }
        return Double.parseDouble(rtn);
    }

    // specific extraction of a value for the number before a semicolon
    public static double valueSemi(String str) {
        String rtn = "";
        for (int i = 0; i < str.length(); i++) {
            if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                rtn += str.substring(i, i+1);
            } if (str.substring(i, i+1).equals("a")) {
                i ++;
            }
            if (str.substring(i, i+1).equals(";")) {
                return Double.parseDouble(rtn);
            }
        }
        return Double.parseDouble(rtn);
    }

    // returns the str with case before the letter F
    public static double value(String str) {
        String rtn = "";
        for (int i = 0; i < str.length(); i++) {
            // adds numbers or decimal points to the return
            if (isNumeric(str.substring(i, i + 1)) || str.substring(i, i + 1).equals(".") || str.substring(i, i+1).equals("-")) {
                rtn += str.substring(i, i+1);
            }
            // if it detects an F, immediate return
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
            // adds only numbers to the return
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

    // check if there exists a URL page on PUBChem for the info I am trying to find
    // For Example:
    /*
     * Some compounds don't have pH's listed or don't have pKas listed, so I wouldn't be able to initialize those!
     */
    public static boolean checkURLResponse(String find, String base) {
        // first create the variable connection
        HttpURLConnection conn = null;
        try {
            // generates the URL for the connection  (with the heading of what you are trying to find)
            URL url = new URL(base + find);

            // open the connection and get the response
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            // if there is an error, return false ; otherwise return true
            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.err.println("No " + find + " Found!");
                return false;
            } else {
                System.err.println(find + " Found!");
                return true;
            }

        // catch any exceptions just in case!
        } catch (IOException e) {
            System.err.println("Error! - " + find);
            e.printStackTrace();
            return false;
        } finally {
            // closes the connection
            if (conn == null) {
                conn.disconnect();
            }
        }

        
    }
}
