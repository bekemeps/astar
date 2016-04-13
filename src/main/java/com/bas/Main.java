package com.bas;

import com.bas.domain.Terrain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main method for A-Star Algorithm application
 *
 * Created by Bekezela on 09/04/2016.
 * IntelliJ IDEA 14.1.6
 */
public class Main {
    public static void main(String[] args) {
        List<Terrain> terrains = readTerrains();
        for (Terrain terrain : terrains) {
            System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
            System.out.println("TERRAIN:");
            System.out.println(terrain.toString());
            terrain.processForBestPath();
            terrain.setOptimalPath();
            System.out.println("OPTIMAL PATH ('#'):");
            System.out.println(terrain.toString());
        }
        writeTerrainOutput(terrains);
    }

    private static void writeTerrainOutput(List<Terrain> terrains) {
        Path file = Paths.get("terrains_output.txt");
        List<String> lines = new ArrayList<>();
        for (Terrain terrain : terrains) {
            lines.add(terrain.toString());
        }
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to read all the terrains from the text file
     *
     * N.B: The text file must have the format such that the terrains are separated by empty lines and two empty lines
     * after the last terrain
     *
     * @return list of Terrain objects
     */
    public static List<Terrain> readTerrains() {
        List<Terrain> terrains = new ArrayList<>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("terrains.txt");
        Scanner scanner = new Scanner(inputStream);

        String line;
        List<String> rows = new ArrayList<>();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                try {
                    terrains.add(new Terrain(rows));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                rows = new ArrayList<>();
            } else {
                rows.add(line);
            }
        }

        return terrains;
    }
}
