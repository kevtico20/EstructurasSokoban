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
import java.util.Stack;
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

            // Guardar Stack
            Stack<String> pila = partida.getPila();
            for (String move : pila) {
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
        Stack<String> pila = new Stack<>();

        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            String line;
            int width = 0;
            int height = 0;

            while ((line = br.readLine()) != null) {
                if (line.equals("---")) {
                    // Fin del tablero, inicializar la LinkedList
                    board = new LinkedList(width, height);
                    br.mark(1000); // Marcar posición actual
                } else if (line.equals("===")) {
                    // Fin de la pila
                    break;
                } else if (board == null) {
                    // Leer dimensiones del tablero
                    width = line.length();
                    height++;
                } else {
                    // Leer contenido del tablero
                    for (int x = 0; x < line.length(); x++) {
                        board.setCell(x, height - 1, line.charAt(x));
                    }
                    br.mark(1000); // Marcar posición actual
                }
            }

            br.reset(); // Volver a la posición marcada
            while ((line = br.readLine()) != null && !line.equals("===")) {
                pila.push(line);
            }

            partida.setBoard(board);
            partida.setPila(pila);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return partida;
    }
}
