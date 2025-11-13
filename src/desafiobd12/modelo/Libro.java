package modelo;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String isbn;
    private boolean disponible;
    
    // Constructor vacío
    public Libro() {}

    // Constructor para inserciones (sin ID)
    public Libro(String titulo, String autor, int anoPublicacion, String isbn, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    // Constructor completo (para lecturas de la BD)
    public Libro(int id, String titulo, String autor, int anoPublicacion, String isbn, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnoPublicacion() { return anoPublicacion; }
    public void setAnoPublicacion(int anoPublicacion) { this.anoPublicacion = anoPublicacion; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return String.format("Libro[ID=%d, Titulo=%s, Autor=%s, Año=%d, Disponible=%b]",
                id, titulo, autor, anoPublicacion, disponible);
    }
} 