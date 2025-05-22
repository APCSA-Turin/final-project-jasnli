package com.example;

import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    public static void main(String[] args) {
        saveData("this is test ");
    }
    public static void saveData(String data) {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
