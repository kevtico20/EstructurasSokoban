/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import una.cr.ac.model.GuardarPartida;
import una.cr.ac.model.LinkedList;

/**
 *
 * @author ArauzKJ
 */
public class ManejoDatos {

    private static final String TXT_PATH = "src/main/java/una/cr/ac/model/datos.txt";

    public static void guardarPartida(GuardarPartida partida) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH, false))) {
            // Guardar LinkedList
            LinkedList board = partida.getBoard();
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    bw.write(board.getCell(j, i));
                }
                bw.newLine();
            }
            bw.write("---");
            bw.newLine();

            // Guardar Vector
            Vector<String> vector = partida.getVector();
            for (String move : vector) {
                bw.write(move);
                bw.newLine();
            }
            bw.write("==="); // Separador de fin de partida
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GuardarPartida leePartida() {
        GuardarPartida partida = new GuardarPartida();
        LinkedList board = null;
        Vector<String> vector = new Vector<>();
        int width = 0;
        int height = 0;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            // Leer el tablero y determinar dimensiones
            while ((line = br.readLine()) != null && !line.equals("---")) {
                if (width == 0) {
                    width = line.length();
                }
                height++;
            }

            // Inicializar la LinkedList con las dimensiones obtenidas
            board = new LinkedList(width, height);

            // Leer el tablero nuevamente
            int currentRow = 0;
            br.mark(10000); // Marcar posición para regresar después de la sección del tablero
            while ((line = br.readLine()) != null && !line.equals("---")) {
                for (int x = 0; x < line.length(); x++) {
                    board.setCell(x, currentRow, line.charAt(x));
                }
                currentRow++;
            }

            // Leer el vector de movimientos
            br.reset(); // Regresar a la posición marcada
            while ((line = br.readLine()) != null && !line.equals("===")) {
                vector.add(line);
            }

            partida.setBoard(board);
            partida.setVector(vector);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return partida;
    }
}
