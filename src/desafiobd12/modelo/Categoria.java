package tienda.modelo;

public class Categoria {
    private int id;
    private String nombre;

    public Categoria() {}

    // Constructor para inserciones (sin ID)
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    
    // Constructor completo
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return String.format("Categoria[ID=%d, Nombre=%s]", id, nombre);
    }
}