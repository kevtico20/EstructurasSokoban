package una.cr.ac.model;

public class GuardarPartida {

    private int nivel;
    private String[] levelData;
    private String archivo; // Nombre del archivo (opcional)
    private int playerX; // Coordenada X del jugador
    private int playerY; // Coordenada Y del jugador

    // Constructor
    public GuardarPartida(int nivel, String[] levelData, int playerX, int playerY) {
        this.nivel = nivel;
        this.levelData = levelData;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    // Getters y Setters
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String[] getLevelData() {
        return levelData;
    }

    public void setLevelData(String[] levelData) {
        this.levelData = levelData;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    // Getters y Setters para la posición del jugador
    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    // Método para obtener la posición del jugador como un string "x,y"
    public String getPlayerPosition() {
        return playerX + "," + playerY;
    }

    // Método para establecer la posición del jugador desde un string "x,y"
    public void setPlayerPosition(String position) {
        String[] coords = position.split(",");
        this.playerX = Integer.parseInt(coords[0]);
        this.playerY = Integer.parseInt(coords[1]);
    }
}
