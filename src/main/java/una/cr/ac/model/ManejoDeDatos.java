package una.cr.ac.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejoDeDatos {

    public static GuardarPartida leePartida() {
        GuardarPartida partida = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/una/cr/ac/levels/partida_guardada.txt"))) {
            String line = br.readLine();
            int nivel = 1;
            int playerX = 0;
            int playerY = 0;

            // Procesar la línea que indica el nivel
            if (line != null && line.startsWith("Nivel:")) {
                nivel = Integer.parseInt(line.split(":")[1].trim());
               
            }

            // Leer el estado del tablero
            List<String> levelData = new ArrayList<>();
            while ((line = br.readLine()) != null && !line.startsWith("Player:")) {
                if (!line.isEmpty()) {
                    levelData.add(line);  // Almacena cada fila del tablero
                }
            }

            // Procesar la línea que indica la posición del jugador
            if (line != null && line.startsWith("Player:")) {
                String[] playerPos = line.split(":")[1].trim().split(",");
                playerX = Integer.parseInt(playerPos[0]);
                playerY = Integer.parseInt(playerPos[1]);
            }

            // Verificación: Mostrar el contenido del tablero leído
            System.out.println("Datos de la partida guardada:");
            for (String row : levelData) {
                System.out.println(row);
            }
            System.out.println("Posición del jugador: (" + playerX + "," + playerY + ")");

            // Crear el objeto GuardarPartida con los datos del nivel, tablero y posición del jugador
            partida = new GuardarPartida(nivel, levelData.toArray(new String[0]), playerX, playerY);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return partida;
    }
}
