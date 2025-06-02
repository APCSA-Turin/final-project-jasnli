package com.example;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


public class API {
    public static String getData(String endpoint) {
        boolean success = false;
        // retries if there is a 503 error
        while (!success) {
            try {
                /*endpoint is a url (string) that you get from an API website*/
                URL url = new URL(endpoint);
                /*connect to the URL*/
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                /*creates a GET request to the API.. Asking the server to retrieve information for our program*/
                connection.setRequestMethod("GET");
                // for APIs like PUBChem, with limited API calls since it is public, this is required
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java)");

                int status = connection.getResponseCode();
            
                // 200 means it worked, otherwise it was rejected
                if (status != 200) {
                    throw new IOException("Server returned status code: " + status + " for URL: " + endpoint);
                }
                /* When you read data from the server, it wil be in bytes, the InputStreamReader will convert it to text. 
                The BufferedReader wraps the text in a buffer so we can read it line by line*/
                BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;//variable to store text, line by line
                /*A string builder is similar to a string object but faster for larger strings, 
                you can concatenate to it and build a larger string. Loop through the buffer 
                (read line by line). Add it to the stringbuilder */
                StringBuilder content = new StringBuilder();
                while ((inputLine = buff.readLine()) != null) {
                    content.append(inputLine);
                }
                success = true;
                buff.close(); //close the bufferreader
                connection.disconnect(); //disconnect from server 
                return content.toString(); //return the content as a string
            } catch (IOException e) {
                // TODO: handle exception
                System.out.println("Server Nonresponsive, wait 1 second!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("Retrying...");
            }
        }
        return "";      
    }
}
