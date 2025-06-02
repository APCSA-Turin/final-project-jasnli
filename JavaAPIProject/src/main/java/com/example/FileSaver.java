package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        // first reads through every line
        try (BufferedReader reader = new BufferedReader(new FileReader("elements.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // splits each line into parts, separated by spaces
                String[] parts = line.split(" ");
                // if that first String is the element name, then update the use
                if (parts[0].equals(name)) {
                    try (FileWriter writer = new FileWriter("elements.txt")) {
                        String str = "";
                        // replaces the whole file with everything before, the modified line, then everything after
                        str += loadFileBefore(name);
                        str += name + " " + uses + "\n";
                        str += loadFileAfter(name);
                        writer.write(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
                
        } catch (IOException e) {
        e.printStackTrace();
        }
    } 

    // loads the elements file up to one point
    // AI did not write this method, in fact Jason Li is very proud to have written this method and the other methods
    public static String loadFileBefore(String itemFound) {
        String str = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("elements.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(itemFound)) {
                    // if we find the item, stop writing
                    return str;
                }
                str += line + "\n";
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    // loads the elements file up after one point
    // AI did not write this method, in fact Jason Li is very proud to have written this method and the other methods
    public static String loadFileAfter(String itemFound) {
        String str = "";
        boolean startWriting = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("elements.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // write the line only if the string was found
                if (startWriting) {
                    str += line + "\n";
                }

                // if we find the item, start writing every line afterwards
                if (line.equals(itemFound)) {
                    startWriting = true;
                }
  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}

