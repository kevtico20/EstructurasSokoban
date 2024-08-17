/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class LevelLoader {

    public String[] loadLevelFromDirectory(String directoryPath, int desiredLevel) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No se encontraron archivos de nivel en el directorio: " + directoryPath);
        }

        for (File file : files) {
            String[] levelData = readLevel(file, desiredLevel);
            if (levelData != null) {
                return levelData; // Retorna el nivel si se encuentra
            }
        }

        throw new IllegalArgumentException("No se encontró ningún archivo que contenga el nivel: " + desiredLevel);
    }

    private String[] readLevel(File file, int expectedLevel) {
        List<String> lineList = new ArrayList<>();
        boolean isLevelFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();  // Leer la primera línea que debería contener "level: X"
            if (line != null && line.startsWith("level:")) {
                int level = Integer.parseInt(line.split(":")[1].trim());
                if (level == expectedLevel) {
                    isLevelFound = true;
                } else {
                    return null; // Si el nivel no es el deseado, retornar null
                }
            } else {
                return null; // Si el archivo no tiene un nivel válido, retornar null
            }

            // Leer las siguientes líneas que representan el tablero
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;  // Fin del nivel
                }
                lineList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lineList.toArray(new String[0]);
    }
}