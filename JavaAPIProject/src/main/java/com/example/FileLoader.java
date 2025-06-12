package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    // reads the stats file and returns (wins, losses)
    public static int[] getStats() {
        // index 0 = wins
        // index 1 = losses
        int[] stats = new int[2];

        try (BufferedReader reader = new BufferedReader(new FileReader("stats.txt"))) {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            // breaks strings down into parts based off where the spaces are
            stats[0] = Integer.parseInt(parts[0]); // the first part, or the wins
            stats[1] = Integer.parseInt(parts[1]); // the second part, or the losses
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;

    }

    // find character frequency
    public static int frequency(String element) {
        int freq = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("elements.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) { // while there are still lines to read
                String[] parts = line.split(" ");
                if (parts[0].equals(element)) { // if the element matches
                    freq = Integer.parseInt(parts[1]); // returns the frequency
                    return freq;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return freq;
    }
}

