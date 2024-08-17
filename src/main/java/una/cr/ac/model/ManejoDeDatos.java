/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class ManejoDeDatos {

    public static GuardarPartida leePartida() {
        GuardarPartida partida = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/una/cr/ac/levels/partida_guardada.txt"))) {
            String line = br.readLine();
            int nivel = 1;

            // Procesar la línea que indica el nivel
            if (line != null && line.startsWith("Nivel:")) {
                nivel = Integer.parseInt(line.split(":")[1].trim());
            }

            // Leer el estado del tablero
            List<String> levelData = new ArrayList<>();
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                levelData.add(line);
            }

            // Verificación: Mostrar el contenido del tablero leído
            System.out.println("Datos de la partida guardada:");
            for (String row : levelData) {
                System.out.println(row);
            }

            // Crear el objeto GuardarPartida con los datos correctos
            partida = new GuardarPartida(nivel, levelData.toArray(new String[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partida;
    }

}
