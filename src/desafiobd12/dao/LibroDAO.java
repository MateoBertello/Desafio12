package biblioteca.dao;

import biblioteca.conexion.ConexionDB;
import biblioteca.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    /**
     * Inserta un nuevo libro (Create)
     */
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, ano_publicacion, isbn, disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setString(4, libro.getIsbn());
            pstmt.setBoolean(5, libro.isDisponible());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los libros (Read)
     */
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros ORDER BY autor, titulo";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }

    /**
     * Busca un libro por ID (Read)
     */
    public Libro obtenerPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return crearLibroDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza un libro (Update)
     */
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, ano_publicacion=?, isbn=?, disponible=? WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setString(4, libro.getIsbn());
            pstmt.setBoolean(5, libro.isDisponible());
            pstmt.setInt(6, libro.getId()); // ID para el WHERE
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un libro (Delete)
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    // --- MÉTODOS ADICIONALES ---

    /**
     * Busca libros por autor
     */
    public List<Libro> obtenerPorAutor(String autor) {
        List<Libro> libros = new ArrayList<>();
        // Usamos LIKE para búsquedas parciales (ej: "García Márquez")
        String sql = "SELECT * FROM libros WHERE autor LIKE ? ORDER BY ano_publicacion";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(crearLibroDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libros por autor: " + e.getMessage());
        }
        return libros;
    }

    /**
     * Lista solo los libros disponibles
     */
    public List<Libro> obtenerDisponibles() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE disponible = TRUE ORDER BY titulo";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libros disponibles: " + e.getMessage());
        }
        return libros;
    }


    /**
     * Método auxiliar para crear un objeto Libro desde un ResultSet
     */
    private Libro crearLibroDesdeResultSet(ResultSet rs) throws SQLException {
        return new Libro(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getInt("ano_publicacion"),
            rs.getString("isbn"),
            rs.getBoolean("disponible")
        );
    }
}