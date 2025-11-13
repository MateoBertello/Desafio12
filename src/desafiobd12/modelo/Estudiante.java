package desafiobd12.modelo;

public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;

    public Estudiante() {}

    public Estudiante(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Estudiante(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    @Override
    public String toString() {
        return String.format("Estudiante[ID=%d, Nombre=%s, Apellido=%s]", 
                             id, nombre, apellido);
    }
}