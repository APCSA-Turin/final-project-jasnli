package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSaver {
    public static void main(String[] args) {
        addWin(5, 2);
        addElementUse("Hydrogen", 0);
    }
    
    // saves win loss data
    public static void addWin(int wins, int losses) {
        try (FileWriter writer = new FileWriter("stats.txt")) {
            writer.write(wins + " " + losses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // saves character played data
    public static void addElementUse(String name, int uses) {

        // makes a new list
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("elements.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2 && parts[0].equals(name)) {
                    // Update the existing element
                    lines.add(name + " " + uses);
                } else {
                    // Keep other lines unchanged
                    lines.add(line);
                }
            }
            
            // Write all lines back to the file
            try (FileWriter writer = new FileWriter("elements.txt")) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}


