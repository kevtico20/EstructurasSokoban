package una.cr.ac.model;

public class GuardarPartida {

    private int nivel;
    private String[] levelData;
    private String archivo; // Nuevo atributo para el nombre del archivo

    // Constructor
    public GuardarPartida(int nivel, String[] levelData) {
        this.nivel = nivel;
        this.levelData = levelData;
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
}
